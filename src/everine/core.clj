(ns everine.core
  (:use [ring.util.response :only [redirect]]
        ring.middleware.resource
        ring.middleware.content-type)
  (:require [compojure.core :refer :all]
            [compojure.route :as route])
  (:gen-class))

(defn wrap-root [handler]
  (fn [req]
    (handler (if (= (:uri req) "/")
               (assoc req :uri "/index.html")
               req))))

(defroutes app-routes
  (route/not-found "<h1>Page not found</h1>"))

(def app
  (-> app-routes
      (wrap-resource "public")
      wrap-content-type
      wrap-root))
