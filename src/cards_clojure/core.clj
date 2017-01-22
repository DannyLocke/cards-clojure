(ns cards-clojure.core
  (:gen-class))

(def suits [:clubs :spades :hearts :diamonds])
(def ranks (range 1 14))

(defn create-deck[]
  (set 
    (for [suit suits
          rank ranks]
      {:suit suit
       :rank rank})))

(defn create-hands [deck]
  (set
    (for [c1 deck
          c2 (disj deck c1)
          c3 (disj deck c1 c2)
          c4 (disj deck c1 c2 c3)]
      #{c1 c2 c3 c4})))

(def test-hand 
  #{{:suit :hearts
     :rank 2}
    {:suit :spades
     :rank 2} 
    {:suit :clubs
     :rank 3}
    {:suit :diamonds
     :rank 4}})

(defn flush? [hand]
  (= 1 (count (set (map :suit hand)))))

(defn straight? [hand]
  (let [ranks (map :rank hand)
        [e f g h] ranks
        sorted (sort [e f g h])
        [a b c d] sorted]
    (= a (- b 1) (- c 2) (- d 3))))

(defn straight-flush? [hand]
  (let [ranks (map :rank hand)
        [e f g h] ranks
        sorted (sort [e f g h])
        [a b c d] sorted]
    (and (= a (- b 1) (- c 2) (- d 3))
         (= 1 (count (set (map :suit hand)))))))

(defn four-of-a-kind? [hand]
  (= 1 (count (set (map :rank hand)))))

(defn three-of-a-kind? [hand]
  (let [ranks (map :rank hand)
        [a b c d] ranks]
    (or (= a b c)
        (= a b d)
        (= a c d)
        (= b c d))))

(defn two-pair? [hand]
  (let [ranks (map :rank hand)
        [a b c d] ranks]
    (or (and (= a b)(= c d))
        (and (= a c)(= b d))
        (and (= a d)(= b c)))))

(defn tests []
  (flush? test-hand))

(defn -main []
  (let [deck (create-deck)
        hands (create-hands deck)
        flush-hands (filter flush? hands)
        straight-flush-hands(filter straight-flush? hands)
        straight-hands (filter straight? hands)
        four-of-a-kind-hands (filter four-of-a-kind? hands)
        three-of-a-kind-hands (filter three-of-a-kind? hands)
        two-pair-hands (filter two-pair? hands)]
    (println (count flush-hands)
      (count straight-flush-hands)
      (count straight-hands)
      (count four-of-a-kind-hands)
      (count three-of-a-kind-hands)
      (count two-pair-hands))))


