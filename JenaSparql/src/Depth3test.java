import java.io.IOException;
import java.math.BigDecimal;

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

public class Depth3test {

	static double[] relationAccuracy = new double[] { 0.9552, 0.9528, 0.9562 , 0.999, 0.959};
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String dir = "/Users/yangfan/Downloads/yago";
		Dataset dataset = TDBFactory.createDataset(dir);

		Model model = dataset.getDefaultModel();

		String var1 = "Gong_Li";
		String var2 = "Zhang_Ziyi";

		String prefix = "<http://yago-knowledge.org/resource/";

		String Complexvar1 = prefix + var1 + ">";
		String Complexvar2 = prefix + var2 + ">";

		// depth = 3
		String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
				+ "SELECT ?t1 ?t2 ?t3 WHERE { "
				+ Complexvar1
				+ " ?t1 ?t2. "
				+ Complexvar2 + " ?t3 ?t2. }";

		Query query = QueryFactory.create(queryString);
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		
		getGoogleResults google = new getGoogleResults();

		for (; results.hasNext();) {
			QuerySolution soln = results.nextSolution();
			RDFNode t1 = soln.get("t1");
			RDFNode t2 = soln.get("t2");
			RDFNode t3 = soln.get("t3");
			String str1 = t1.toString().replace("http://yago-knowledge.org/resource/", "");
			String str2 = t2.toString().replace("http://yago-knowledge.org/resource/", "");
			String str3 = t3.toString().replace("http://yago-knowledge.org/resource/", "");

			if (str2.contains("http://www.w3.org/2000/01/rdf-schema#")
					|| str2.contains("http://www.w3.org/2000/01/rdf-schema#")
					|| str2.contains("http://www.w3.org/2000/01/rdf-schema#")) {
				continue;
			}

			str1 = str1.replace("http://www.w3.org/1999/02/22-rdf-syntax-ns#","");
			str2 = str2.replace("http://www.w3.org/1999/02/22-rdf-syntax-ns#","");
			str3 = str3.replace("http://www.w3.org/1999/02/22-rdf-syntax-ns#","");

			if ((str1.equals("actedIn") || str1.equals("hasWonPrize") || str1.equals("directed") || str1.equals("wasBornIn") || str1.equals("hasGender"))
				&& (str3.equals("actedIn") || str3.equals("hasWonPrize") || str3.equals("directed") || str3.equals("wasBornIn") || str3.equals("hasGender"))) {
				System.out.println("t1::" + str1);
				System.out.println("t2::" + str2);	
				System.out.println("t3::" + str3);
				double beta = 0.5;
				
				double first = google.calInfo(var1, str1, str2);
				double second = google.calInfo(var2, str3, str2);
				double Pinfo = first * second;

				double Pconf = 1;
				
			if (str1.equals("actedIn")) {
				if(str3.equals("actedIn")) Pconf = relationAccuracy[0] * relationAccuracy[0];
				if(str3.equals("directed")) Pconf = relationAccuracy[0] * relationAccuracy[1];
				if(str3.equals("hasWonPrize")) Pconf = relationAccuracy[0] * relationAccuracy[2];
				if(str3.equals("hasGender")) Pconf = relationAccuracy[0] * relationAccuracy[3];
				if(str3.equals("wasBornIn")) Pconf = relationAccuracy[0] * relationAccuracy[4];
			}
			if (str1.equals("directed")) {
				if(str3.equals("actedIn")) Pconf = relationAccuracy[1] * relationAccuracy[0];
				if(str3.equals("directed")) Pconf = relationAccuracy[1] * relationAccuracy[1];
				if(str3.equals("hasWonPrize")) Pconf = relationAccuracy[1] * relationAccuracy[2];
				if(str3.equals("hasGender")) Pconf = relationAccuracy[1] * relationAccuracy[3];
				if(str3.equals("wasBornIn")) Pconf = relationAccuracy[1] * relationAccuracy[4];
			}
			if (str1.equals("hasWonPrize")) {
				if(str3.equals("actedIn")) Pconf = relationAccuracy[2] * relationAccuracy[0];
				if(str3.equals("directed")) Pconf = relationAccuracy[2] * relationAccuracy[1];
				if(str3.equals("hasWonPrize")) Pconf = relationAccuracy[2] * relationAccuracy[2];
				if(str3.equals("hasGender")) Pconf = relationAccuracy[2] * relationAccuracy[3];
				if(str3.equals("wasBornIn")) Pconf = relationAccuracy[2] * relationAccuracy[4];
			}
			if (str1.equals("hasGender")) {
				if(str3.equals("actedIn")) Pconf = relationAccuracy[3] * relationAccuracy[0];
				if(str3.equals("directed")) Pconf = relationAccuracy[3] * relationAccuracy[1];
				if(str3.equals("hasWonPrize")) Pconf = relationAccuracy[3] * relationAccuracy[2];
				if(str3.equals("hasGender")) Pconf = relationAccuracy[3] * relationAccuracy[3];
				if(str3.equals("wasBornIn")) Pconf = relationAccuracy[3] * relationAccuracy[4];
			}
			if (str1.equals("wasBornIn")) {
				if(str3.equals("actedIn")) Pconf = relationAccuracy[4] * relationAccuracy[0];
				if(str3.equals("directed")) Pconf = relationAccuracy[4] * relationAccuracy[1];
				if(str3.equals("hasWonPrize")) Pconf = relationAccuracy[4] * relationAccuracy[2];
				if(str3.equals("hasGender")) Pconf = relationAccuracy[4] * relationAccuracy[3];
				if(str3.equals("wasBornIn")) Pconf = relationAccuracy[4] * relationAccuracy[4];
			}
				
				double P = beta * Pconf + (1 - beta) * Pinfo;
				BigDecimal bg = new BigDecimal(P);
				P = bg.setScale(8, BigDecimal.ROUND_HALF_UP).doubleValue();
				System.out.println("P::"+P);
			}
		}
		
	}

}
