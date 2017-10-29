(ns everine.core
  (:use [ring.util.response :only [redirect]]
        ring.middleware.resource
        ring.middleware.content-type)
  (:gen-class))


(defn handler [request]
  (redirect "/index.html"))

(def app
  (-> handler
      (wrap-resource "public")
      wrap-content-type))
