def counts = [:]
new File('./sampled_patient_visits.csv').splitEachLine('\t') {
println it[1]
  if(!counts[it[1]]) { counts[it[1]]= 0}
  counts[it[1]]++
}
println counts.findAll{k,v->v==1}.size()
