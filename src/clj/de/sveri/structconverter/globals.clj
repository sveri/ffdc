(ns de.sveri.structconverter.globals
  (:require [de.sveri.clojure.commons.files.edn :as commons]))

(def ^:const friendui-config-name "friendui-config.edn")

(def ^:const friendui-config (commons/from-edn friendui-config-name))

(def ^:const partition-id (:partition-id friendui-config))
(def ^:const db-uri (:db-uri friendui-config))


(def ^:const username-kw (:username-kw friendui-config))
(def ^:const pw-kw (:pw-kw friendui-config))
(def ^:const activated-kw (:activated-kw friendui-config))
(def ^:const role-kw (:role-kw friendui-config))
(def ^:const activationid-kw :user/activationid)

(def ^:const uuid-kw :adspread/uuid)