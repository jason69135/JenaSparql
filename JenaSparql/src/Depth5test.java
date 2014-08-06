import java.io.IOException;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.tdb.TDBFactory;

public class Depth5test {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String dir = "/Users/yangfan/Downloads/yago";
		Dataset dataset = TDBFactory.createDataset(dir);
		
		Model model = dataset.getDefaultModel();
		
		String var1 = "Gong_Li";
		String var2 = "Zhang_Ziyi";
		
		String prefix = "<http://yago-knowledge.org/resource/";
		
		String Complexvar1 = prefix+var1+">";
		String Complexvar2 = prefix+var2+">";
		
		//depth = 4

		String queryString = 
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
				"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"+
				"SELECT ?t1 ?t2 ?t3 ?t4 ?t5 ?t6 ?t7 WHERE { "+Complexvar1+" ?t1 ?t2. ?t3 ?t4 ?t2. ?t3 ?t5 ?t6."+Complexvar2+" ?t7 ?t6."
						+ "FILTER ( ?t1 != 'type') }";

		Query query = QueryFactory.create(queryString);
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		
		getGoogleResults google = new getGoogleResults();
		for(;results.hasNext();){
			QuerySolution soln = results.nextSolution();
			RDFNode t1 = soln.get("t1");
			RDFNode t2 = soln.get("t2");
			RDFNode t3 = soln.get("t3");
			RDFNode t4 = soln.get("t4");
			RDFNode t5 = soln.get("t5");
			RDFNode t6 = soln.get("t6");
			RDFNode t7 = soln.get("t7");
			String str1 = t1.toString().replace("http://yago-knowledge.org/resource/", "");
			String str2 = t2.toString().replace("http://yago-knowledge.org/resource/", "");
			String str3 = t3.toString().replace("http://yago-knowledge.org/resource/", "");
			String str4 = t4.toString().replace("http://yago-knowledge.org/resource/", "");
			String str5 = t5.toString().replace("http://yago-knowledge.org/resource/", "");
			String str6 = t6.toString().replace("http://yago-knowledge.org/resource/", "");
			String str7 = t7.toString().replace("http://yago-knowledge.org/resource/", "");
			if(str1.contains("http://www.w3.org/2000/01/rdf-schema#") || str2.contains("http://www.w3.org/2000/01/rdf-schema#") || str3.contains("http://www.w3.org/2000/01/rdf-schema#")
					||str4.contains("http://www.w3.org/2000/01/rdf-schema#") || str5.contains("http://www.w3.org/2000/01/rdf-schema#") || str6.contains("http://www.w3.org/2000/01/rdf-schema#")
					||str7.contains("http://www.w3.org/2000/01/rdf-schema#")){
					continue;
				}
			
			str1 = str1.replace("http://www.w3.org/1999/02/22-rdf-syntax-ns#", "");
			str2 = str2.replace("http://www.w3.org/1999/02/22-rdf-syntax-ns#", "");
			str3 = str3.replace("http://www.w3.org/1999/02/22-rdf-syntax-ns#", "");
			str4 = str4.replace("http://www.w3.org/1999/02/22-rdf-syntax-ns#", "");
			str5 = str5.replace("http://www.w3.org/1999/02/22-rdf-syntax-ns#", "");
			str6 = str6.replace("http://www.w3.org/1999/02/22-rdf-syntax-ns#", "");
			str7 = str7.replace("http://www.w3.org/1999/02/22-rdf-syntax-ns#", "");

			if((str1.equals("actedIn") || str1.equals("hasWonPrize")|| str1.equals("directed") || str1.equals("wasBornIn"))
			&& (str4.equals("actedIn") || str4.equals("hasWonPrize")|| str4.equals("directed") || str4.equals("wasBornIn"))		
			&& (str5.equals("actedIn") || str5.equals("hasWonPrize")|| str5.equals("directed") || str5.equals("wasBornIn"))
			&& (str7.equals("actedIn") || str7.equals("hasWonPrize")|| str7.equals("directed") || str7.equals("wasBornIn"))
			){
				System.out.println("t1::"+str1);
				System.out.println("t2::"+str2);
				System.out.println("t3::"+str3);
				System.out.println("t4::"+str4);
				System.out.println("t5::"+str5);
				System.out.println("t6::"+str6);
				System.out.println("t7::"+str7);
			}
			double aa = google.calInfo(var1, str1, str2);
			double bb = google.calInfo(str3, str4, str2);
			double cc = google.calInfo(str3, str5, str6);
			double dd = google.calInfo(var2, str7, str6);

			System.out.println("aa::"+aa);
			System.out.println("bb::"+bb);
			System.out.println("cc::"+cc);
			System.out.println("dd::"+dd);
		}
	}
}
