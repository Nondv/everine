(ns everine.core
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.content-type :refer [wrap-content-type]]
            [ring.middleware.resource :refer [wrap-resource]])
  (:gen-class))

(defn wrap-root [handler]
  #(handler (if (not= (:uri %) "/")
              %
              (assoc % :uri "/index.html"))))

(defroutes app-routes
  (route/not-found "<h1>Page not found</h1>"))

(def app
  (-> app-routes
      (wrap-resource "public")
      wrap-content-type
      wrap-root))
