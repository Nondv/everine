(ns nondv.vk-auth.core)

(def api-version "5.68")

(def permission-map
  {:notify        1
   :friends       2
   :photos        4
   :audio         8
   :video         16
   :app-widget    64
   :pages         128
   :app-link      256
   :status        1024
   :notes         2048
   :offline       65536
   :docs          131072
   :groups        262144
   :notifications 524288
   :stats         1048576
   :email         4194304
   :market        134217728})

(defn scope-value
  "Generates number for vk `scope` parameter.
   `permissions` is an array of keywords from `permission-map`"
  [& permissions]
  (reduce + 0 (map #(% permission-map) permissions)))

(defn oauth-link
  [{client-id     :client-id
    redirect-uri  :redirect-uri
    state         :state
    scope         :scope
    :or           {state "whatever"
                   scope 0}}]
  (str "https://oauth.vk.com/authorize?"
       "display=page"
       "&response_type=code"
       "&state=" state
       "&client_id=" client-id
       "&redirect_uri=" redirect-uri ; TODO: encode
       "&scope=" scope
       "&v=" api-version))

(defn access-token-link
  [{client-id     :client-id
    client-secret :client-secret
    redirect-uri  :redirect-uri
    code          :code}]
  (str "https://oauth.vk.com/access_token?"
       "client_id=" client-id
       "&client_secret=" client-secret
       "&redirect_uri=" redirect-uri
       "&code=" code))
