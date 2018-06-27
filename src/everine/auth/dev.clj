(ns everine.auth.dev
  (:require [compojure.core :refer :all]
            [ring.util.response :refer [redirect]]))

;; Every adapter should define:
;; * `routes-with-disabled-auth` - list of strings with routes that should be
;;   excluded from `everine.auth/wrap-auth`. Normally they match routes from
;;   `additional-routes`
;; * `additional-routes` - ring handler normally created via `defroutes`.
;; * `login-handler` - function that will be invoked on /login

(def routes-with-disabled-auth nil)
(def additional-routes nil)

(defn new-session []
  {:user-id 123})

(defn login-handler [_request]
  (assoc (redirect "/") :session (new-session)))
