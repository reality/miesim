// Here we will select 1,000 patient visits...

def all_patient_visits = [:]
def sampled_patients = []
def mapped = []
new File('./doid_icd_mappings.txt').splitEachLine('\t') { mapped << it[0].replace('.','') }

new File('./DIAGNOSES_ICD.csv').splitEachLine(',') { f ->
  def key = f[1] + '_' + f[2]
  if(!all_patient_visits.containsKey(key)) {
    all_patient_visits[key] = [] 
  }
  all_patient_visits[key] << f[4]
}

def rng = new Random()
pKeys = all_patient_visits.keySet().collect()

def already = []

while(sampled_patients.size() < 1000) {
  def key = pKeys[rng.nextInt(all_patient_visits.size())]
  def pt = all_patient_visits[key]
  if(!pt[0]) { continue; }
  def pDiag = pt[0].replaceAll('"', '')
  println pDiag
  if(new File('new_texts/' + key + '.txt').exists() && !already.contains(key) && mapped.contains(pDiag)) {
    sampled_patients << key + '\t' + pt.join(',')
    already << key
  }
}

new File('sampled_patient_visits.csv').text = sampled_patients.join('\n')
