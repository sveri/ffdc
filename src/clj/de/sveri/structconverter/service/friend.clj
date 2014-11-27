(ns de.sveri.structconverter.service.friend
  (:require [cemerick.friend.credentials :as creds]
            [cemerick.friend.workflows :as workflows]
            [de.sveri.friendui.routes.user :as friend-routes]
            [de.sveri.friendui-datomic.db :as f-d-db]))

(derive :user/admin :user/free)

(defn friend-settings [db-val]
  {:credential-fn             (partial creds/bcrypt-credential-fn (partial f-d-db/login-user db-val))
   :workflows                 [(workflows/interactive-form)]
   :login-uri                 "/user/login"
   :unauthorized-redirect-uri "/user/login"
   :unauthorized-handler      (fn [_] (friend-routes/unauthorized-access))
   :default-landing-uri       "/"})

(defn request->username [req]
  (get-in req [:session :cemerick.friend/identity :current]))

