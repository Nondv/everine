(ns everine.auth.vk
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

(defn- get-json [uri]
  (cheshire/parse-string (slurp uri) true))

(defn- get-access-data [code]
  (get-json
   (vk-auth/access-token-link {:client-id      client-id
                               :client-secret  client-secret
                               :redirect-uri   auth-redirect-uri
                               :code           code})))

(defn session-from-access-data
  ([access-data] (session-from-access-data access-data nil))
  ([access-data session] (assoc session :user-id (str "vk_" (:user_id access-data)))))

(defn login-handler [_request]
  (redirect (vk-auth/oauth-link {:client-id client-id
                                 :redirect-uri auth-redirect-uri})))

(def routes-with-disabled-auth [auth-redirect-path])
(defroutes additional-routes
  (GET auth-redirect-path request
       (let [access-data   (get-access-data (get-in request [:params "code"]))
             new-session   (session-from-access-data access-data (:session request))]
         (assoc (redirect "/") :session new-session))))
