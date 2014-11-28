(ns de.sveri.structconverter.routes.csv
  (:require [compojure.core :refer [routes GET]]
            [net.cgrand.enlive-html :refer [defsnippet]]
            [de.sveri.friendui-datomic.db :as f-d-db]
            [de.sveri.structconverter.db :as db]
            [de.sveri.structconverter.routes.base :as base]))

(defn index [db-val uri]
  (base/base {:title "Transform CSV Files" :uri uri :init "de.sveri.structconverter.csv.foo();"}
             (f-d-db/get-loggedin-user-role db-val)))

(defn csv-routes [db-conn]
  (routes
    (GET "/csv" [] (index (db/get-db-val db-conn) "/csv"))))