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


public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String dir = "/Users/yangfan/Downloads/yago";
		Dataset dataset = TDBFactory.createDataset(dir);
		
		Model model = dataset.getDefaultModel();
		
		String var1 = "Gong_Li";
		String var2 = "Zhang_Ziyi";
		
		String prefix = "<http://yago-knowledge.org/resource/";
		
		var1 = prefix+var1+">";
		var2 = prefix+var2+">";
		
		//depth = 2
		String queryString = 
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
				"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"+
				"SELECT ?t1 ?t2  WHERE { "+var1+" ?t1 ?t2. "+var2+" ?t1 ?t2.}";

		Query query = QueryFactory.create(queryString);
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		for(;results.hasNext();){
			QuerySolution soln = results.nextSolution();
			RDFNode t1 = soln.get("t1");
			RDFNode t2 = soln.get("t2");
//			RDFNode t3 = soln.get("t3");
//			RDFNode t4 = soln.get("t4");
			String str1 = t1.toString().replace("http://yago-knowledge.org/resource/", "");
			String str2 = t2.toString().replace("http://yago-knowledge.org/resource/", "");
			str1 = str1.replace("http://www.w3.org/1999/02/22-rdf-syntax-ns#", "");
			str2 = str2.replace("http://www.w3.org/1999/02/22-rdf-syntax-ns#", "");
//			String str3 = t3.toString().replace("http://yago-knowledge.org/resource/", "");
//			String str4 = t4.toString().replace("http://yago-knowledge.org/resource/", "");
			System.out.println("t1::"+str1);
			System.out.println("t2::"+str2);
//			System.out.println("t3::"+str3);
//			System.out.println("t4::"+str4);
			
		}
	}
}
