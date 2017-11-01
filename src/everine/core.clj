(ns everine.core
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
            [ring.util.response :refer [response]]
            [ring.middleware.content-type :refer [wrap-content-type]]
            [ring.middleware.resource :refer [wrap-resource]])
  (:gen-class))

(defn wrap-root [handler]
  #(handler (if (not= (:uri %) "/")
              %
              (assoc % :uri "/index.html"))))

(def lists {:current "test list"
            :lists [{:name "test list"
                     :items [{:id 1 :label "do something"}
                             {:id 2 :label "do nothing" :done true}]}
                    {:name "empty list"
                     :items [{:id 1 :label "got you!" :done true}]}]})

(defn- save-data [data]
  (println data)
  data)

(defroutes app-routes
  (GET "/lists.json" [req] (response lists))
  (POST "/lists.json" request ((comp response save-data :body) request))
  (route/not-found "<h1>Page not found</h1>"))

(def app
  (-> app-routes
      wrap-json-body
      wrap-json-response
      (wrap-resource "public")
      wrap-content-type
      wrap-root))
