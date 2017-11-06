(ns everine.core
  (:require [everine.db :as db]
            [everine.auth :as auth]
            [environ.core :refer [env]]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
            [ring.util.response :refer [response]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.session :refer [wrap-session]]
            [ring.middleware.content-type :refer [wrap-content-type]]
            [ring.middleware.resource :refer [wrap-resource]])
  (:gen-class))

(defn wrap-root [handler]
  #(handler (if (not= (:uri %) "/")
              %
              (assoc % :uri "/index.html"))))

(defn- save-data [data]
  (println data)
  data)

(defroutes app-routes
  (GET "/lists.json" request (response (db/user-data (auth/current-user request))))
  (POST "/lists.json" request
        (println (:body request))
        (response {:ok (db/set-user-data (auth/current-user request) (:body request))}))
  (route/not-found "<h1>Page not found</h1>"))

(def app
  (-> (routes auth/auth-routes app-routes)
      wrap-json-body
      wrap-json-response
      (wrap-resource "public")
      wrap-content-type
      auth/wrap-auth
      wrap-session
      wrap-params
      wrap-root))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty app {:port port :join? false})))
