package edu.wpi.cs.jburge.SEURAT.rationaleData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

import org.eclipse.swt.widgets.Display;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

import SEURAT.events.RationaleUpdateEvent;
import SEURAT.events.RationaleElementUpdateEventGenerator;

import edu.wpi.cs.jburge.SEURAT.editors.EditPattern;

public class Pattern extends RationaleElement {
	
	PatternElementType type;
	
	private int problemcategory;
	
	String problem;
	
	String context;
	
	String solution;
	
	String implementation;
	
	String example;
	
	String url;
	
	public Vector<OntEntry> posiOnts;
	
	public Vector<OntEntry> negaOnts;
	
	public Vector<OntEntry> ontEntries;
	
	public Vector<PatternDecision> subDecisions;
	
	private RationaleElementUpdateEventGenerator<Pattern> m_eventGenerator = 
		new RationaleElementUpdateEventGenerator<Pattern>(this);
		
	public Pattern()
	{
		super();
		posiOnts = new Vector<OntEntry>();
		negaOnts = new Vector<OntEntry>();
		ontEntries = new Vector<OntEntry>();
		description = "";
		problem = "";
		context = "";
		solution = "";
		implementation = "";
		example = "";
		url = "";
		subDecisions = new Vector<PatternDecision>();
	} 
	
	public RationaleElementType getElementType(){
		return RationaleElementType.PATTERN;
	}
	
	public void setType(PatternElementType newtype)
	{
		type = newtype;
	}
	
	public PatternElementType getType(){
		return type;
	}
	
	public void setProblem(String prob){
		problem = prob;
	}
	
	public String getProblem(){
		return problem;
	}
	
	public void setContext(String con){
		context = con;
	}
	
	public String getContext(){
		return context;
	}
	
	public void setSolution(String solu){
		solution = solu;
	}
	
	public String getSolution(){
		return solution;
	}
	
	public void setImplementation(String imple){
		implementation = imple;
	}
	
	public String getImplementation(){
		return implementation;
	}
	
	public void setExample(String exa){
		example = exa;
	}
	
	public String getExample(){
		return example;
	}
	
	public int getProblemCategory(){
		return problemcategory;
	}
	
	public void setProblemCategory(int newCategory){
		problemcategory = newCategory;
	}
	
//	public void setPosiOnt(OntEntry ont){
//		if (posiOnt != null)
//		{
//			posiOnt.decRefs();
//		}
//		posiOnt = ont;
//		ont.incRefs(); 
//	}
	
	public Vector<OntEntry> getOntEntries() {
		return ontEntries;
	}

	public void addOntEntries(OntEntry oe) {
		ontEntries.add(oe);
	}

	public void addPosiOnt(OntEntry oe){
		posiOnts.add(oe);
	}	
	
	public Vector<OntEntry> getPosiOnts(){
		return posiOnts;
	}
	
	public void addNegaOnt(OntEntry oe){
		negaOnts.add(oe);
	}	
	
	public Vector<OntEntry> getNegaOnts(){
		return negaOnts;
	}
	
	
	
//	public void setNegaOnt(OntEntry ont){
//		if (negaOnt != null)
//		{
//			negaOnt.decRefs();
//		}
//		negaOnt = ont;
//		ont.incRefs(); 
//	}
	
	public Vector<PatternDecision> getSubDecisions() {
		return subDecisions;
	}

	public void setSubDecisions(Vector<PatternDecision> subDecisions) {
		this.subDecisions = subDecisions;
	}
	
	public void addSubDecision(PatternDecision subD){
		this.subDecisions.add(subD);
	}

	public void setNegaOnts(Vector<OntEntry> negaOnts) {
		this.negaOnts = negaOnts;
	}

	public void setOntEntries(Vector<OntEntry> ontEntries) {
		this.ontEntries = ontEntries;
	}

	public void setPosiOnts(Vector<OntEntry> posiOnts) {
		this.posiOnts = posiOnts;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public Element toXML(Document ratDoc){
		Element patternE;
		RationaleDB db = RationaleDB.getHandle();
		
		//Now, add pattern to doc
		String entryID = db.getRef(this);
		if (entryID == null){
			entryID = db.addPatternRef(this);
		}
		
		patternE = ratDoc.createElement("DR:pattern");
		patternE.setAttribute("rid", entryID);
		patternE.setAttribute("name", name);
		
		//Now, add child elements of this... (other than refs, which are handled in RationaleDBUtil)
		
		//Type
		Element typeE = ratDoc.createElement("type");
		Text typeText = ratDoc.createTextNode(type.toString());
		typeE.appendChild(typeText);
		patternE.appendChild(typeE);
		
		//description
		Element descE = ratDoc.createElement("description");
		Text descText = ratDoc.createTextNode(description);
		descE.appendChild(descText);
		patternE.appendChild(descE);
		
		//problem
		Element probE = ratDoc.createElement("problem");
		Text probText = ratDoc.createTextNode(problem);
		probE.appendChild(probText);
		patternE.appendChild(probE);
		
		//context
		Element contE = ratDoc.createElement("context");
		Text contText = ratDoc.createTextNode(context);
		contE.appendChild(contText);
		patternE.appendChild(contE);
		
		//Solution
		Element solE = ratDoc.createElement("solution");
		Text solText = ratDoc.createTextNode(solution);
		solE.appendChild(solText);
		patternE.appendChild(solE);
		
		//Implementation
		Element implE = ratDoc.createElement("implementation");
		Text implText = ratDoc.createTextNode(implementation);
		implE.appendChild(implText);
		patternE.appendChild(implE);
		
		//Example
		Element examE = ratDoc.createElement("example");
		Text examText = ratDoc.createTextNode(example);
		examE.appendChild(examText);
		patternE.appendChild(examE);
		
		Element urlE = ratDoc.createElement("url");
		Text urlText = ratDoc.createTextNode(url);
		urlE.appendChild(urlText);
		patternE.appendChild(urlE);
		
		//Use the database problem category ID instead.
		Element categoryE = ratDoc.createElement("refCategory");
		Text categoryText = ratDoc.createTextNode("c" + new Integer(problemcategory).toString());
		categoryE.appendChild(categoryText);
		patternE.appendChild(categoryE);
		
		//Add reference id of positive ontology
		Iterator<OntEntry> posi = posiOnts.iterator();
		while (posi.hasNext()){
			OntEntry cur = posi.next();
			Element curE = ratDoc.createElement("refOntPos");
			Text curText = ratDoc.createTextNode("r" + new Integer(cur.getID()));
			curE.appendChild(curText);
			patternE.appendChild(curE);
		}
		
		//Add reference id of negative ontology
		Iterator<OntEntry> negi = negaOnts.iterator();
		while (negi.hasNext()){
			OntEntry cur = negi.next();
			Element curE = ratDoc.createElement("refOntNeg");
			Text curText = ratDoc.createTextNode("r" + new Integer(cur.getID()));
			curE.appendChild(curText);
			patternE.appendChild(curE);
		}
		
		//Add reference id of pattern decisions
		Iterator<PatternDecision> deci = subDecisions.iterator();
		while (deci.hasNext()){
			PatternDecision cur = deci.next();
			Element curE = ratDoc.createElement("refChildDecision");
			Text curText = ratDoc.createTextNode("pd" + new Integer(cur.getID()));
			curE.appendChild(curText);
			patternE.appendChild(curE);
		}
		
		return patternE;
	}

	public void fromDatabase(int patternID)
	{
		RationaleDB db = RationaleDB.getHandle();
		Connection conn = db.getConnection();
		
		this.id = patternID;
		
		Statement stmt = null; 
		ResultSet rs = null; 
		String findQuery = ""; 
		try {
			stmt = conn.createStatement();
			
			findQuery = "SELECT name  FROM " +
			"patterns where id = " +
			new Integer(patternID).toString();

			rs = stmt.executeQuery(findQuery);
			
			if (rs.next())
			{
				name = RationaleDBUtil.decode(rs.getString("name"));
				rs.close();
				this.fromDatabase(name);
			}
			
		} catch (SQLException ex) {
			// handle any errors 
			RationaleDB.reportError(ex,"Error in Pattern.fromDatabase(1)", findQuery);
			ex.printStackTrace();
		}
		finally { 
			RationaleDB.releaseResources(stmt, rs);
		}
		
	}
	
	public void fromDatabase(String name)
	{		
		RationaleDB db = RationaleDB.getHandle();
	
		ResultSet rs = null;
		
		String findQuery = "";
		
		Statement stmt = null;
		
		Connection conn = null;
		
		try {
			db.getStatement_PatternFromDB().setString(1, name);
			rs = db.getStatement_PatternFromDB().executeQuery();
			conn = db.getConnection();
			stmt = conn.createStatement();
			if (rs.next())
			{	
				this.id = rs.getInt("id");
				
				problemcategory = db.getCategoryByPattern(id);
				
				this.name = name;				
				
				try {
					this.description = RationaleDBUtil.decode(new String(rs
							.getBlob("description").getBytes(1,
									(int) rs.getBlob("description").length())));
					//this.description = rs.getString("description");
				} catch (NullPointerException e) {
					this.description = "";
				}				
				try {
					this.problem = RationaleDBUtil.decode(new String(rs
							.getBlob("problem").getBytes(1,
									(int) rs.getBlob("problem").length())));
				} catch (NullPointerException e) {
					this.problem = "";
				}				
				try {
					this.context = RationaleDBUtil.decode(new String(rs
							.getBlob("context").getBytes(1,
									(int) rs.getBlob("context").length())));
				} catch (NullPointerException e) {
					this.context = "";
				}				
				try {
					this.solution = RationaleDBUtil.decode(new String(rs
							.getBlob("solution").getBytes(1,
									(int) rs.getBlob("solution").length())));
				} catch (NullPointerException e) {
					this.solution = "";
				}				
				try {
					this.example = RationaleDBUtil.decode(new String(rs
							.getBlob("example").getBytes(1,
									(int) rs.getBlob("example").length())));
				} catch (NullPointerException e) {
					this.example = "";
				}
				
				try {
					this.implementation = RationaleDBUtil.decode(new String(
							rs.getBlob("implementation")
									.getBytes(
											1,
											(int) rs.getBlob("implementation")
													.length())));
				} catch (NullPointerException e) {
					this.implementation = "";
				}				
				this.type = PatternElementType.fromString(rs.getString("type"));
				
				try {
					this.url = RationaleDBUtil.decode(rs.getString("url"));
				} catch (NullPointerException e) {
					this.url = "";
				}
				
				// Now, get the associated ontology entries
				findQuery = "SELECT * FROM pattern_ontentries WHERE patternID = " + this.id;
				rs = stmt.executeQuery(findQuery);
				while (rs.next()){
					if(rs.getString("direction") != null){
					if ((rs.getString("direction")).compareTo("IS") == 0){
						OntEntry ontEn = new OntEntry();
						ontEn.fromDatabase(rs.getInt("ontID"));
						posiOnts.add(ontEn);
						ontEntries.add(ontEn);
					}else{
						OntEntry ontEn = new OntEntry();
						ontEn.fromDatabase(rs.getInt("ontID"));
						negaOnts.add(ontEn);
						ontEntries.add(ontEn);
					}
					}
				}
				//TODO using id or name for patterndecisions and onts?
				findQuery = "SELECT * FROM patterndecisions WHERE parent = " + this.id;
				rs = stmt.executeQuery(findQuery);
				while (rs.next()){
					PatternDecision decision = new PatternDecision();
					decision.fromDatabase(rs.getInt("id"));
					addSubDecision(decision);
				}		
			}			
		} catch (SQLException ex) {
			RationaleDB.reportError(ex, "Pattern.fromDatabase(String)", "Pattern:FromDatabase");
			ex.printStackTrace();
		}
		finally { 
			RationaleDB.releaseResources(null,rs);
		}		
		
	}
	
	public boolean display(Display disp){
		EditPattern ep = new EditPattern(disp, this, true);
		return ep.getCanceled();
	}
	
	public void toDatabase(int pid){
		
		RationaleDB db = RationaleDB.getHandle();
		Connection conn = db.getConnection();
		
		int ourid = 0;

		// Update Event To Inform Subscribers Of Changes
		// To Rationale
		RationaleUpdateEvent l_updateEvent;
		
		//find out if this ontology entry is already in the database
		Statement stmt = null; 
		ResultSet rs = null; 
		
		try {
			
			if (inDatabase())
			{
				//Take special care when inserting blobs! (re-implemented) (YQ)
				PreparedStatement ps = conn.prepareStatement("UPDATE patterns SET description = ?, problem = ?, context = ?, solution = ?, example = ?, implementation = ?, type = ?, url = ? WHERE name = ?");
				
				ps.setBytes(1, description.getBytes());
				ps.setBytes(2, problem.getBytes());
				ps.setBytes(3, context.getBytes());
				ps.setBytes(4, solution.getBytes());
				ps.setBytes(5, example.getBytes());
				ps.setBytes(6, implementation.getBytes());
				ps.setString(7,type.toString());
				ps.setString(8, url);
				ps.setString(9, name);
				ps.executeUpdate();
				ps.close();
				
				stmt = conn.createStatement(); 
				//saving associated ontentries
				//delete associated ones
				String deleteonts = "DELETE FROM pattern_ontentries where patternID = " + this.id;
				stmt.execute(deleteonts);
				
				//positive ones
				Enumeration pOnts = getPosiOnts().elements();
				while(pOnts.hasMoreElements()){
					String updatePosiOnts = "INSERT INTO pattern_ontentries (patternID, ontID, direction) VALUES (" + this.id + "," + ((OntEntry)pOnts.nextElement()).getID() + "," + "'IS')";
					stmt.execute(updatePosiOnts);
				}
//				//negative ones
				Enumeration nOnts = getNegaOnts().elements();
				while(nOnts.hasMoreElements()){
					String updateNegaOnts = "INSERT INTO pattern_ontentries (patternID, ontID, direction) VALUES (" + this.id + "," + ((OntEntry)nOnts.nextElement()).getID() + "," + "'NOT')";
					stmt.execute(updateNegaOnts);
				}
				
				//Now, update pattern_problemcategory
				//Delete all pre-existing entry in the relationship then insert a new row
				//containing the new category.
				stmt = conn.createStatement();
				String deleteExisting = "DELETE from pattern_problemcategory where patternID = " + id + "";
				stmt.execute(deleteExisting);
				
				l_updateEvent = m_eventGenerator.MakeUpdated();
			}else{
				//new pattern
				//TODO only for creating new patterns under pattern library, so don't care about ontology entries and sub-decisions???
				//TODO I doubt this code works. Have to re-implement this if we want to insert patterns (YQ)	
				PreparedStatement ps = conn.prepareStatement("INSERT INTO patterns " +
						"(name, type, description, problem, context, solution, implementation, example, url) " +
						"VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)");
				ps.setString(1, name);
				ps.setString(2, type.toString());
				ps.setBytes(3, description.getBytes());
				ps.setBytes(4, problem.getBytes());
				ps.setBytes(5, context.getBytes());
				ps.setBytes(6, solution.getBytes());
				ps.setBytes(7, implementation.getBytes());
				ps.setBytes(8, example.getBytes());
				ps.setString(9, url);
				ps.executeUpdate();
				ps.close();
				System.out.println("insert new pattern done!");
				
				//Now, update pattern_problemcategory
				//First, get the id of the pattern, then use the id of the pattern to insert a new relationship between pattern and the associated pattern problem category.
				stmt = conn.createStatement();
				String getID = "select id from patterns where name = '" + name + "' and type = '" + type.toString() + "'";
				rs = stmt.executeQuery(getID);
				if (rs.next()){
					id = rs.getInt("id");
				}
				else {
					System.err.println("Warning: id of the pattern does not exist!");
				}
				l_updateEvent = m_eventGenerator.MakeCreated();
			}
			//Finally, it's time to add the pattern_problemcategory relationship
			String addNewPCRelation = RationaleDBCreate.pattern_problemCategoryInsert("" + id + ", " + problemcategory + "");
			stmt.execute(addNewPCRelation);
			
			m_eventGenerator.Broadcast(l_updateEvent);
				
		} catch (SQLException ex) {
			RationaleDB.reportError(ex, "Pattern.toDatabase(int)", "SQL Error");
			ex.printStackTrace();
		}		
		finally { 
			RationaleDB.releaseResources(stmt, rs);
		}		
	}
	
	public void fromXML(Element patternE)
	{
		this.fromXML = true;
		
		RationaleDB db = RationaleDB.getHandle();
		
		//add idref ***from the XML***
		String idref = patternE.getAttribute("id");
		
		
		//get our name
		name = patternE.getAttribute("name");
		
//		get our type
		type = PatternElementType.fromString(patternE.getAttribute("type"));

		//get description
		Node  childE = patternE.getFirstChild();
		
		//the text is actually the child of the element, odd...
		//set description
		Node descN = childE.getFirstChild();
		if (descN instanceof Text) 
		{
			Text text = (Text) descN;
			String data = text.getData();
			setDescription(data);
		}
		
		//set problem
		childE = (Element) childE.getNextSibling();
		Node probN = childE.getFirstChild();
		if (probN instanceof Text) 
		{
			Text text = (Text) probN;
			String data = text.getData();
			setProblem(data);
		}
		
		//set context
		childE = (Element) childE.getNextSibling();
		Node contN = childE.getFirstChild();
		if (contN instanceof Text) 
		{
			Text text = (Text) contN;
			String data = text.getData();
			setContext(data);
		}
		
		//set solution
		childE = (Element) childE.getNextSibling();
		Node soluN = childE.getFirstChild();
		if (soluN instanceof Text) 
		{
			Text text = (Text) soluN;
			String data = text.getData();
			setSolution(data);
		}
		
		//set implementation
		childE = (Element) childE.getNextSibling();
		Node impleN = childE.getFirstChild();
		if (impleN instanceof Text) 
		{
			Text text = (Text) impleN;
			String data = text.getData();
			setImplementation(data);
		}
		
		//set example
		childE = (Element) childE.getNextSibling();
		Node examN = childE.getFirstChild();
		if (examN instanceof Text) 
		{
			Text text = (Text) examN;
			String data = text.getData();
			setExample(data);
		}
	}
	
	/**
	 * Check if this pattern is already in the database. The check is different
	 * if you are reading it in from XML because you can do a query on the name.
	 * Otherwise you can't because you run the risk of the user having changed the
	 * name. 
	 * 
	 * @return true if in the database already
	 */	
	private boolean inDatabase()
	{
		boolean found = false;
		String findQuery = "";
		
		if (fromXML)
		{
			RationaleDB db = RationaleDB.getHandle();
			Connection conn = db.getConnection();
			
			//find out if this pattern is already in the database
			Statement stmt = null; 
			ResultSet rs = null; 
			
			try {
				stmt = conn.createStatement(); 
				findQuery = "SELECT id FROM patterns where name='" +
				this.name + "'";
				rs = stmt.executeQuery(findQuery); 
				
				if (rs.next())
				{
					int ourid;
					ourid = rs.getInt("id");
					this.id = ourid;
					found = true;
				}
			}catch (SQLException ex) {
				RationaleDB.reportError(ex, "Pattern.inDatabase", findQuery); 
			}finally { 
				RationaleDB.releaseResources(stmt, rs);
			}
		}
		//If we aren't reading it from the XML, just check the ID
		//checking the name like above won't work because the user may 
		//have modified the name!
		else if (this.getID() >= 0)
		{
			found = true;
		}
		return found;
	}
}