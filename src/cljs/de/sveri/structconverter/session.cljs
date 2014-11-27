(ns de.sveri.structconverter.session
  (:refer-clojure :exclude [get])
  (:require [freactive.core :refer [atom cursor]]))

(def state (atom {}))

(def current-page (cursor state :current-page))

(defn get [k & [default]]
  (clojure.core/get @state k default))

(defn put! [k v]
  (swap! state assoc k v))

(defn update-in! [ks f & args]
  (clojure.core/swap!
    state
    #(apply (partial update-in % ks f) args)))