(ns everine.components.todo-list-form
  (:require [rum.core :as rum]
            [everine.elements :refer [text-input submit-button]]))

(defn- todo-list [name]
  {:name name :items []})

(rum/defcs todo-list-form <
  (rum/local "" ::name)
  [state f]
  (let [name-atom (::name state)
        name @name-atom
        change-name! #(reset! name-atom %)
        on-submit (fn [event]
                    (.preventDefault event)
                    (change-name! "")
                    (f (todo-list name)))]
    [:form
     {:class "todo-list-form"
      :on-submit on-submit}
     (text-input "Name" name "todo-list-form__input" change-name!)
     (submit-button "Add list" "todo-list-form__submit")]))
