(ns everine.auth
  (:require [nondv.vk-auth.core :as vk-auth]
            [environ.core :refer [env]]
            [cheshire.core :as cheshire]
            [compojure.core :refer :all]
            [ring.util.response :refer [redirect]]))

(def host "https://everine.herokuapp.com")
(def auth-redirect-path "/authorization_redirect")
(def auth-redirect-uri (str host auth-redirect-path))

(def client-secret (env :vk-app-secret))
(def client-id     (env :vk-app-id))

(defn vk-user-id [request]
  (get-in request [:session :vk-user-id]))

(defn authorized? [request]
  (vk-user-id request))

(def not-authorized? (complement authorized?))

(defn- get-json [uri]
  (cheshire/parse-string (slurp uri) true))

(defn- get-access-data [code]
  (get-json
   (vk-auth/access-token-link {:client-id      client-id
                               :client-secret  client-secret
                               :redirect-uri   auth-redirect-uri
                               :code           code})))

(defn current-user [request]
  (when (authorized? request)
    (str "vk_" (vk-user-id request))))

(defn- need-auth? [request except-routes]
  (not (some #(= % (:uri request))
             (into ["/login" auth-redirect-path] except-routes))))

(defn wrap-auth
  "Redirects to /login if user not logged in"
  ([handler] (wrap-auth handler []))
  ([handler except-routes] #(if (and (need-auth? % except-routes) (not-authorized? %))
                              (redirect "/login")
                              (handler %))))

(defn session-from-access-data
  ([access-data] (session-from-access-data access-data nil))
  ([access-data session] (assoc session :vk-user-id (:user_id access-data))))

(defroutes auth-routes
  (GET "/login" request
       (redirect (vk-auth/oauth-link {:client-id client-id
                                      :redirect-uri auth-redirect-uri})))
  (GET auth-redirect-path request
       (let [access-data   (get-access-data (get-in request [:params "code"]))
             new-session   (session-from-access-data access-data (:session request))]
         (assoc (redirect "/") :session new-session))))
