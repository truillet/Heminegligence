REM % 1 = configuration.xml / %2= id_sujet / %3 = session  / %4=condition / %5 = experimentateur / %6 = timer

java -cp .;./jar/Hemineg_2.1.jar fr.irit.ihcs.experiment.Heminegligence %1 %2 %3 %4 %5 %6 >> res.txt
