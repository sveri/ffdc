(ns de.sveri.structconverter.routes.home
  (:require [compojure.core :refer [routes GET]]
            [de.sveri.structconverter.dev :refer [is-dev? inject-devmode-html]]
            [de.sveri.structconverter.routes.base :as base]
            [net.cgrand.enlive-html :as html]
            [de.sveri.friendui-datomic.db :as f-d-db]
            [de.sveri.structconverter.db :as db]))

(html/defsnippet contact-snip (str base/template-path "home/contact.html") [:#content] [])
(defn contact [db-val uri]
  (base/base {:title "Contact" :content (contact-snip) :uri uri} (f-d-db/get-loggedin-user-role db-val)))

(html/defsnippet tos-snip (str base/template-path "home/tos.html") [:#content] [])
(defn tos [db-val uri]
  (base/base {:title "Terms Of Service" :content (tos-snip) :uri uri} (f-d-db/get-loggedin-user-role db-val)))

(html/defsnippet agb-snip (str base/template-path "home/agb.html") [:#content] [])
(defn agb [db-val uri]
  (base/base {:title "AGB" :content (agb-snip) :uri uri} (f-d-db/get-loggedin-user-role db-val)))

(html/defsnippet cookies-snip (str base/template-path "home/cookies.html") [:#content] [])
(defn cookies [db-val uri]
  (base/base {:title "Cookies" :content (cookies-snip) :uri uri} (f-d-db/get-loggedin-user-role db-val)))

(defn index [db-val uri]
  (base/base {:title "Title" :uri uri} (f-d-db/get-loggedin-user-role db-val)))

(defn home-routes [db-conn]
    (routes
      (GET "/contact" [] (contact (db/get-db-val db-conn) "/contact"))
      (GET "/tos" [] (tos (db/get-db-val db-conn) "/tos"))
      (GET "/agb" [] (agb (db/get-db-val db-conn) "/agb"))
      (GET "/cookies" [] (cookies (db/get-db-val db-conn) "/cookies"))))
