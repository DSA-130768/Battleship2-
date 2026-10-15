package battleship;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ToPDF {

    private static class RegistoJogada { //RegistoJogada guarda o número da jogada e os tiros disparados
        int numero;
        List<IPosition> tiros;

        public RegistoJogada(int numero, List<IPosition> tiros) {
            this.numero = numero;
            this.tiros = tiros;
        }
    }

    private static final List<RegistoJogada> jogadasJogo = new ArrayList<>();
    private static final String Hist = "historico_jogadas.pdf";

    public static void exportarJogada(int numeroMove, List<IPosition> shots) {//aqui é listada a jogada no PDF
        jogadasJogo.add(new RegistoJogada(numeroMove,shots));

        try (PDDocument doc = new PDDocument()) {// criacao de um novo PDF com uma pagina em branco
            PDPage page = new PDPage();
            doc.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(doc, page)) {
                contentStream.beginText();//isto permite comecar a escrever no PDF

                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                contentStream.newLineAtOffset(50, 750);//ponto onde se começa a escrever

                for (RegistoJogada reg : jogadasJogo) {
                    String linha = "Rajada " + reg.numero + ": " + reg.tiros.toString(); //String final a ser adicionada
                    contentStream.showText(linha);//escreve no doc
                    contentStream.newLineAtOffset(0, -20);// nova linha para nova rajada
                }

                contentStream.endText();
            }

            // Guarda o ficheiro
            doc.save(Hist);
            System.out.println("PDF atualizado com a jogada: " +numeroMove);

        } catch (IOException e) {
            System.err.println("Erro ao atualizar o documento PDF: " + e.getMessage());
        }
    }
}