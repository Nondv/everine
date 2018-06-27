(ns everine.auth
  (:require [everine.auth.dev :as dev-adapter]
            [everine.auth.vk :as vk-adapter]
            [environ.core :refer [env]]
            [compojure.core :refer :all]
            [ring.util.response :refer [redirect]]))
(def current-env (env :env))
(def production? (= current-env "production"))

(defn user-id [request]
  (get-in request [:session :user-id]))

(defn authorized? [request]
  (user-id request))

(def not-authorized? (complement authorized?))

(defn current-user [request]
  (when (authorized? request)
    (user-id request)))

(def routes-with-disabled-auth
  (filter
   identity
   (flatten ["/login"
             (when-not production? dev-adapter/routes-with-disabled-auth)
             vk-adapter/routes-with-disabled-auth])))

(defn- need-auth? [request except-routes]
  (not (some #(= % (:uri request))
             (into routes-with-disabled-auth except-routes))))

(defn wrap-auth
  "Redirects to /login if user not logged in"
  ([handler] (wrap-auth handler []))
  ([handler except-routes] #(if (and (need-auth? % except-routes) (not-authorized? %))
                              (redirect "/login")
                              (handler %))))


(def adapters-additional-routes
  (apply routes (filter
                 identity
                 [(when-not production? dev-adapter/additional-routes)
                  vk-adapter/additional-routes])))

(defn handle-login [request]
  (if production?
    (vk-adapter/login-handler request)
    (dev-adapter/login-handler request)))

(defroutes auth-routes
  adapters-additional-routes
  (GET "/login" request (handle-login request)))
