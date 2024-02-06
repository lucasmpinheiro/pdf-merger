package org.acme;

import java.io.ByteArrayOutputStream;
import java.io.File;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.file.GenericFile;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;

@ApplicationScoped
@Named
public class AppendContentProcessor implements Processor {

  @Override
  public void process(Exchange exchange) throws Exception {
    @SuppressWarnings("unchecked")
    final GenericFile<File> file = (GenericFile<File>) exchange.getIn().getBody();

    final var document = PDDocument.load(file.getFile());

    final var pdfMerger = new PDFMergerUtility();
    pdfMerger.appendDocument(document, PDDocument.load(new File("./files/contentToAppend.pdf")));

    final var outputStream = new ByteArrayOutputStream();
    document.save(outputStream);
    document.close();

    exchange.getMessage().setHeaders(exchange.getIn().getHeaders());
    exchange.getMessage().setBody(outputStream);
  }
}
