(ns de.sveri.structconverter.server
  (:use [noir.validation :only [wrap-noir-validation]]
        [noir.cookies :only [wrap-noir-cookies]]
        [noir.session :only [clear! mem wrap-noir-session wrap-noir-flash]]
        [ring.middleware.session.memory :only [memory-store]])
  (:require [de.sveri.structconverter.service.friend :as friend-service]
            [net.cgrand.reload :refer [auto-reload]]
            [de.sveri.structconverter.dev :refer [is-dev? inject-devmode-html browser-repl start-figwheel]]
            [compojure.core :refer [GET defroutes routes]]
            [compojure.route :refer [resources]]
            [compojure.handler :refer [site api]]
            [net.cgrand.enlive-html :refer [deftemplate]]
            [ring.middleware.reload :as reload]
            [environ.core :refer [env]]
            [org.httpkit.server :refer [run-server]]
            [datomic.api :as d]
            [de.sveri.friendui.routes.user :refer [friend-routes]]
            [de.sveri.structconverter.routes.home :as home-routes]
            [de.sveri.structconverter.db :as db]
            [de.sveri.structconverter.globals :as glob]
            [cemerick.friend :as friend]
            [de.sveri.friendui.globals :as f-global]
            [de.sveri.structconverter.routes.base :as base]))

(alter-var-root #'f-global/base-template (fn [_] base/base))


(defn get-all-routes []
  (->
    (let [db-conn (d/connect glob/db-uri)]
      (routes (friend-routes (db/FrienduiStorageImpl db-conn))
              (home-routes/home-routes db-conn)
              (GET "/*" req (home-routes/index (db/get-db-val db-conn) "/"))))
    (friend/authenticate (friend-service/friend-settings (db/get-db-val-fn (d/connect glob/db-uri))))
    (wrap-noir-validation)
    (wrap-noir-cookies)
    (wrap-noir-flash)
    (wrap-noir-session
      (update-in {:session-options {:timeout          (* 60 30)
                                    :timeout-response (noir.response/redirect "/")}}
                 [:store] #(or % (memory-store mem))))))

(defroutes routes-def
           (resources "/")
           (resources "/react" {:root "react"})
           (get-all-routes))

(def http-handler
  (if is-dev?
    (reload/wrap-reload (api #'routes-def))
    (api routes-def)))

(defn run [& [port]]
  (defonce ^:private server
           (do
             (if is-dev? (do (auto-reload *ns*) (start-figwheel)))
             (let [port (Integer. (or port (env :port) 10555))]
               (print "Starting web server on port" port ".\n")
               (run-server http-handler {:port  port
                                         :join? false}))))
  server)

(defn -main [& [port]]
  (run port))

(defn start-all []
  (run)
  (browser-repl))
