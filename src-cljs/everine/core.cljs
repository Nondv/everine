(ns everine.core
  (:use [everine.components.todo-list :only [todo-list]])
  (:require [rum.core :as rum]))

(enable-console-print!)

(println "Hello World!")
(println "This is ClojureScript!")

(rum/defc label [text]
   [:div {:class "label"} text])


(def items [{:id 1 :label "item1"} {:id 2 :label "item2"} {:id 3 :label "item3"}])
(def items-atom (atom items))

(rum/defcs app < (rum/local items ::items) [state]
  [:div [(rum/with-key (label "Yarrr!!") 1)
         (rum/with-key (todo-list (::items state)) 2)]])

(rum/mount (app) (js/document.getElementById "app" ))
