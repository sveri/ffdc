(ns de.sveri.structconverter.db
  (:require [datomic.api :as d]
            [de.sveri.friendui-datomic.storage :as f-d-storage]))

(defn get-db-val-fn [db-conn]
  (fn [] (d/db db-conn)))

(defn get-db-val [db-conn]
  (d/db db-conn))

(defn FrienduiStorageImpl [db-conn] (f-d-storage/->FrienduiStorage db-conn (get-db-val-fn db-conn)))

(comment
  ;execute this in the project repl to initialize the db

  (require '[datomic.api :as d])
  (def uri "datomic:dev://localhost:4334/db")
  (d/delete-database uri)
  (d/create-database uri)
  (def conn (d/connect uri))
  (def schema-tx (read-string (slurp "resources/db/datomic-schema.edn")))
  @(d/transact conn schema-tx)

  (def data-tx (read-string (slurp "resources/db/datomic-prod-data.edn")))
  @(d/transact conn data-tx)
  )
