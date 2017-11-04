(ns everine.components.new-todo-item-form
  (:require [rum.core :as rum]
            [everine.elements :refer [text-input submit-button]]))

(defn- item [text]
  {:label text})

(rum/defcs new-todo-item-form <
  (rum/local "" ::text)
  [state f]
  (let [text-atom (::text state)
        text @text-atom
        change-text! #(reset! text-atom %)
        on-submit (fn [event]
                    (.preventDefault event)
                    (change-text! "")
                    (f (item text)))]
    [:form
     {:class "new-todo-item-form"
      :on-submit on-submit}
     (text-input "Description" text "new-todo-item-form__input" change-text!)
     (submit-button "Add item" "new-todo-item-form__submit")]))
