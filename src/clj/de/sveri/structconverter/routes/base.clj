(ns de.sveri.structconverter.routes.base
  (:require [net.cgrand.enlive-html :as html]
            [de.sveri.structconverter.dev :refer [is-dev? inject-devmode-html]]
            [de.sveri.friendui.service.user :as f-user]))

(def template-path "templates/")

(def logged-in-menu [{:url "/test-link" :text "Test Link" :role :user/free}
                     {:url "/user/admin" :text "User Administration" :role :user/admin}])

(def footer-menu [{:url "/contact" :text "Contact"}
                  {:url "/tos" :text "Terms Of Service"}
                  {:url "/agb" :text "AGB"}
                  {:url "/cookies" :text "Cookies"}])

(defmacro maybe-substitute
  ([expr] `(if-let [x# ~expr] (html/substitute x#) identity))
  ([expr & exprs] `(maybe-substitute (or ~expr ~@exprs))))

(defmacro maybe-content
  ([expr] `(if-let [x# ~expr] (html/content x#) identity))
  ([expr & exprs] `(maybe-content (or ~expr ~@exprs))))

(html/defsnippet header-logout-snippet (str template-path "header/header-logout.html") [:#logout] [username]
                 [:a#username-link] (html/content username))

(html/defsnippet header-login-snippet (str template-path "header/header-login-form.html") [:#login-form] [])

(defn fill-base-header []
  (if (f-user/is-logged-in?) (header-logout-snippet (f-user/get-logged-in-username)) (header-login-snippet)))


(html/defsnippet header-menu-link-snippet (str template-path "header/header-menu.html") [(html/attr= :field "link-list")]
                 [uri {:keys [url text]}]
                 [(html/attr= :field "link-list")] (if (= url uri) (html/set-attr :class "active") identity)
                 [(html/attr= :field "link-list") :a] (html/do-> (html/set-attr :href url) (html/content text)))

(html/defsnippet header-menu-snippet (str template-path "header/header-menu.html") [(html/attr= :field "menu")]
                 [uri role]
                 [:#header-menu-wrapper] (html/content
                            (map #(when (isa? role (:role %)) (header-menu-link-snippet uri %)) logged-in-menu)))

(html/defsnippet header-menu-login-wrapper-snippet (str template-path "header/header-menu-login-wrapper.html")
                 [:#menu-login-wrapper]
                 [uri role]
                 [:#login-form-header] (maybe-substitute (fill-base-header))
                 [:#header-menu] (when (f-user/is-logged-in?) (maybe-substitute (header-menu-snippet uri role))))

(html/deftemplate base (str template-path "base.html")
                  [{:keys [title content uri]} & [role]]
                  [:#title] (maybe-content title)
                  [:#header-menu-login-wrapper] (html/content (header-menu-login-wrapper-snippet uri role))
                  [:#footer-menu-wrapper] (html/content (map #(header-menu-link-snippet uri %) footer-menu))
                  [:#content] (maybe-substitute content)
                  [:body] (if is-dev? inject-devmode-html identity))

