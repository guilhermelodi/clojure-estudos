(ns curso3.logic)

(defn sala-disponivel?
  [hospital sala]
  (-> hospital
      (get sala)
      count
      (< 5)))

(defn chega-em
  [hospital sala pessoa]
  (if (sala-disponivel? hospital sala)
    (update hospital sala conj pessoa)
    (throw (ex-info "Fila já está cheia" {:tentando-adicionar pessoa}))))

(defn chega-em-pausado
  [hospital sala pessoa]
  (Thread/sleep (* (rand) 2000))
  (if (sala-disponivel? hospital sala)
    (do                                                     ;
      ; (Thread/sleep 1000)
      (update hospital sala conj pessoa))
    (throw (ex-info "Fila já está cheia" {:tentando-adicionar pessoa}))))

(defn chega-em-pausado-logando
  [hospital sala pessoa]
  (println "Tentando adicionar a pessoa" pessoa)
  (Thread/sleep (* (rand) 2000))
  (if (sala-disponivel? hospital sala)
    (do                                                     ;
      ; (Thread/sleep 1000)
      (println "Dando update com a pessoa" pessoa)
      (update hospital sala conj pessoa))
    (throw (ex-info "Fila já está cheia" {:tentando-adicionar pessoa}))))

(defn atende
  [hospital sala]
  (update hospital sala pop))

(defn proxima-pessoa
  [hospital sala]
  ;(peek (get hospital sala)) ; refiz com threading
  (-> hospital
      sala
      peek))

(defn transfere
  [hospital sala-origem sala-destino]
  (let [pessoa (proxima-pessoa hospital sala-origem)]
    (println "transfere pessoa" pessoa)
    ;(chega-em (atende hospital sala-origem) sala-destino pessoa) ; refiz com threading
    (-> hospital
        (atende sala-origem)
        (chega-em sala-destino pessoa))))

(defn atende-completo
  [hospital sala]
  {:pessoa   (update hospital sala peek)
   :hospital (update hospital sala pop)})

; juxt invoca várias funções com o parâmetro informado
(defn atende-completo-juxt
  [hospital sala]
  (let [fila (get hospital sala)
        peek-pop (juxt peek pop)
        [pessoa fila-atualizada] (peek-pop fila)
        hospital-atualizado (update hospital assoc sala fila-atualizada)]
    {:pessoa   pessoa
     :hospital hospital-atualizado}))