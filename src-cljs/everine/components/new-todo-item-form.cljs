(ns everine.components.new-todo-item-form
  (:require [rum.core :as rum]
            [everine.utils :as utils]))

(defn- submit-button []
  [:input {:type :submit
           :value "Add item"
           :class "new-todo-item-form__submit"}])

(defn- text-input [value on-change]
  [:input {:type :text
           :value value
           :on-change #(on-change (utils/event-target-value %))
           :class "new-todo-item-form__input"} ])

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
     (text-input text change-text!)
     (submit-button)]))
