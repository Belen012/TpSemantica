package com.ontotext.graphdb.example.app.semantica;

import com.ontotext.graphdb.example.util.QueryUtil;
import com.ontotext.graphdb.example.util.UpdateUtil;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.query.*;
import org.eclipse.rdf4j.query.impl.SimpleBinding;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.RepositoryException;
import org.eclipse.rdf4j.repository.http.HTTPRepository;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFParseException;

import javax.script.Bindings;
import java.io.IOException;

/**
 * An example that illustrates loading of ontologies, data, querying and modifying data.
 */
public class SemanticaApp {
    private RepositoryConnection connection;

    public SemanticaApp(RepositoryConnection connection) {
        this.connection = connection;
    }

    /**
     * Loads the ontology and the sample data into the repository.
     *
     * @throws RepositoryException
     * @throws IOException
     * @throws RDFParseException
     */
    public void loadData() throws RepositoryException, IOException, RDFParseException {
        System.out.println("# Loading ontology and data");

        // When adding data we need to start a transaction
        connection.begin();

        // Adding the family ontology
        connection.add(SemanticaApp.class.getResourceAsStream("/tp_semantica-corregido.ttl"), "urn:base", RDFFormat.TURTLE);

        connection.commit();
    }

    public void getSolicitud() {
        System.out.println("Obtener solicitudes");

        TupleQueryResult result = QueryUtil.evaluateSelectQuery(connection,
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                        "SELECT ?subject ?object\n" +
                        "WHERE { ?subject rdfs:subClassOf ?object \n" +
                        "}"
        );
        int contP1 = 0, contP2 = 0, contP3 = 0, contP4 = 0, contP5 = 0;

        while (result.hasNext()) {
            BindingSet bindingSet = result.next();
            String p1 = bindingSet.getBinding("subject").getValue().toString();
            String p2 = bindingSet.getBinding("object").getValue().toString();

            if (p1.contains("Rechazada") && contP1 == 0) {
                contP1++;
                System.out.println("SolicitudRechazada " + p1);
            }
            if (p1.contains("MediaBeca") && contP2 <= 3) {
                contP2++;
                System.out.println("SolicitudMediaBeca"+contP2 + " " + p1);
            }
            if (p1.contains("EnEstudio") && contP3 == 0) {
                contP3++;
                System.out.println("SolicitudEnEstudio " + p1);
            }
            if (p1.contains("BecaTotal") && contP4 == 0) {
                contP4++;
                System.out.println("SolicitudBecaTotal " + p1);
            }
            if (p2.contains("Solicitud") && contP5 == 0) {
                contP5++;
                System.out.println("Las becas clasificadas son del tipo: Solicitud " + p2);
            }

        }
    }

    public void getPostulante() {
        System.out.println("Obtener postulantes");

        TupleQueryResult result = QueryUtil.evaluateSelectQuery(connection,
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                        "SELECT ?subject ?object\n" +
                        "WHERE { ?subject rdfs:subClassOf ?object \n" +
                        "}"
        );

        while (result.hasNext()) {
            BindingSet bindingSet = result.next();
            String p1 = bindingSet.getBinding("subject").getValue().toString();
            String p2 = bindingSet.getBinding("object").getValue().toString();

            if (p2.contains("Postulante") ) {
                System.out.println("Postulante " + p2);
            }

            if (p1.contains("Postulante")) {
                System.out.println("Postulante " + p1);
            }

        }
    }

    public static void main(String[] args) throws Exception {
        HTTPRepository repository = new HTTPRepository("http://DESKTOP-E297TQ4:7200/repositories/tpSemantica-Eceiza-Martinez-Triverio");

        // Separate connection to a repository
        RepositoryConnection connection = repository.getConnection();

        // Clear the repository before we start
        connection.clear();

        SemanticaApp semanticaApp = new SemanticaApp(connection);
        if(connection.isOpen()){
            System.out.println("¡Conexion exitosa!");
        }

        try {
            semanticaApp.loadData();

            System.out.println("################ Consulta 1 ################");
            semanticaApp.getSolicitud();

            System.out.println(" ");
            System.out.println("################ Consulta 2 ################");
            semanticaApp.getPostulante();

        } finally {
            // It is best to close the connection in a finally block
            connection.close();
        }
    }
}
