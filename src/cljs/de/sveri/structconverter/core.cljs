(ns de.sveri.structconverter.core
  (:require [freactive.core :refer [atom cursor]]
            [freactive.dom :as dom]
            [goog.dom :as gdom]
            [de.sveri.structconverter.session :as session])
  (:require-macros [freactive.macros :refer [rx]]
                   [secretary.core :refer [defroute]]))

;define some routes
;(defroute "/" [] (session/put! :current-page index-content))

(defonce root (dom/append-child! (gdom/getElement "content") [:div]))

(defn init-app []
  ;do some initialization here
  )

(defn main-page []
  [:div "main"])

(defn main []
  (init-app)
  ;(session/put! :current-page ) define initial page to load
  (dom/mount! root (main-page)))
