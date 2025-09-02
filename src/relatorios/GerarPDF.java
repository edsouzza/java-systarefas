package relatorios;

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;
import conexao.ConnConexao;
import static biblioteca.VariaveisPublicas.sql;
import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


public class GerarPDF 
{
    ConnConexao conexao   = new ConnConexao();
    Document documento    = new Document();
    Font font             = new Font(Font.TIMES_ROMAN,18,Font.BOLD,new Color(120,100,200));
    Font font1            = new Font(Font.TIMES_ROMAN,12,Font.BOLD,new Color(0,0,0));
    File caminhoDoPDF     = null;
    int cont              = 0;
    
    private File getCaminhoPDF()
    {
        //abre opção de escolha de diretorio para criar o PDF
        JFileChooser escolhaDiretorio  = new JFileChooser();        
        
        int i = escolhaDiretorio.showOpenDialog(escolhaDiretorio);
        try{
            if (i == JFileChooser.APPROVE_OPTION) {
                caminhoDoPDF = escolhaDiretorio.getSelectedFile();    
                JOptionPane.showMessageDialog(null, "O arquivo PDF foi gerado com sucesso em :"+caminhoDoPDF+".pdf");
                cont = 1; //se setar o caminho do PDF corretamente seta 1 na variavel cont liberando assim a amostra do PDF
                return caminhoDoPDF;
            }else{
                JOptionPane.showMessageDialog(null, "Erro, não foi possível gerar o PDF!");
                 return null;
            }        
       
        } catch( NullPointerException npE ) {
               return null;
        }
    }
    
    public void GerarPDFSECOES()
    {        
        //Recebe o caminho do PDF
        getCaminhoPDF();    
        
        try {
            //conectando os dados
            conexao.conectar();
            sql = "SELECT * FROM tblSecoes Where status='ATIVO' ORDER BY nome";
            conexao.ExecutarPesquisaSQL(sql);
            
            //instanciando o documento com o relatorio nele       
            OutputStream relatorio = new FileOutputStream(caminhoDoPDF+".pdf");
            PdfWriter writer       = PdfWriter.getInstance(documento, relatorio); 
            
            //DEFININDO ORIENTAÇÃO DA PAGINA COMO RETRATRO 
            //documento.setPageSize(PageSize.A4);
            
            //DEFININDO ORIENTAÇÃO DA PAGINA COMO PAISAGEM
            documento.setPageSize(PageSize.A4.rotate());          
            
            // criando cabecalho obs:(cabeçalhos e rodapés devem ser adicionados antes que o documento seja aberto) false para ignorar o numero da pagina
            HeaderFooter header = new HeaderFooter(new Phrase("RELATÓRIO DE SEÇÕES ATIVAS", font), false);
            header.setBorder(Rectangle.NO_BORDER);
            header.setAlignment(Element.ALIGN_CENTER);
            documento.setHeader(header);  
                         
            // criando rodapé obs:(cabeçalhos e rodapés devem ser adicionados antes que o documento seja aberto) true para mostrar o numero da pagina
            HeaderFooter footer = new HeaderFooter(new Phrase("Página : ", font1), true);
            //footer.setBorder(Rectangle.NO_BORDER);
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setBorderWidthTop(1);
            footer.setBorderWidthBottom(0);
            documento.setFooter(footer);  
            
            //abrindo o documento
            documento.open();   //abrindo o documento                                    
                        
            //definindo a quantidade de colunas do relatorio em 5 colunas (nome, email,pr, simproc, sei) com um array de floats chamado
            //colsWidth, sendo que cada elemento trata o tamanho de cada coluna
            //float[] colsWidth = {1.8f, 4.3f, 1.1f, 1.1f, 0.8f};
            float[] colsWidth = {2.8f, 6.1f, 1.5f, 1.7f, 4.2f};
            
            //criando a tabela com a qde e o tamanho das colunas definidas acima
            PdfPTable tabela     = new PdfPTable(colsWidth);
            
            //definindo a largura total da tabela
            //tabela.setTotalWidth(550);
            tabela.setTotalWidth(700);
            tabela.setLockedWidth(true);   
            
            //adicionando o cabecalho das colunas ao documento
            tabela.addCell("Nome");
            tabela.addCell("Email");
            tabela.addCell("PR");
            tabela.addCell("Simproc");
            tabela.addCell("Sei");            
                        
            //mostrando os dados das colunas em suas celulas
            while(conexao.rs.next())
            {
                tabela.addCell(conexao.rs.getString("nome"));
                tabela.addCell(conexao.rs.getString("email"));
                tabela.addCell(conexao.rs.getString("pr"));
                tabela.addCell(conexao.rs.getString("simproc"));
                tabela.addCell(conexao.rs.getString("sei"));
            }  
                                  
            //adiciona a tabela ao documento
            documento.add(tabela); 
            
            //fechando o documento
            documento.close(); 
            
            //fechando a conexao
            conexao.desconectar();
         
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }catch (DocumentException ex) {
                Logger.getLogger(GerarPDF.class.getName()).log(Level.SEVERE, null, ex);
            }
         catch (SQLException ex) {
                Logger.getLogger(GerarPDF.class.getName()).log(Level.SEVERE, null, ex);
            }
        //abrindo o PDF depois de criado e mostrando na tela
        try {
            if(cont==1)Desktop.getDesktop().open(new File(caminhoDoPDF+".pdf"));
        } catch (IOException ex) {
            Logger.getLogger(GerarPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void GerarPDFSECOESINATIVADAS()
    {        
        //Recebe o caminho do PDF
        getCaminhoPDF();    
        
        try {
            //conectando os dados
            conexao.conectar();
            sql = "SELECT * FROM tblSecoes Where status='INATIVO' ORDER BY nome";
            conexao.ExecutarPesquisaSQL(sql);
            
            //instanciando o documento com o relatorio nele       
            OutputStream relatorio = new FileOutputStream(caminhoDoPDF+".pdf");
            PdfWriter writer       = PdfWriter.getInstance(documento, relatorio); 
            
            //DEFININDO ORIENTAÇÃO DA PAGINA COMO RETRATRO 
            //documento.setPageSize(PageSize.A4);
            
            //DEFININDO ORIENTAÇÃO DA PAGINA COMO PAISAGEM
            documento.setPageSize(PageSize.A4.rotate());        
            
            // criando cabecalho obs:(cabeçalhos e rodapés devem ser adicionados antes que o documento seja aberto) false para ignorar o numero da pagina
            HeaderFooter header = new HeaderFooter(new Phrase("RELATÓRIO DE SEÇÕES INATIVAS", font), false);
            header.setBorder(Rectangle.NO_BORDER);
            header.setAlignment(Element.ALIGN_CENTER);
            documento.setHeader(header);  
                         
            // criando rodapé obs:(cabeçalhos e rodapés devem ser adicionados antes que o documento seja aberto) true para mostrar o numero da pagina
            HeaderFooter footer = new HeaderFooter(new Phrase("Página : ", font1), true);
            //footer.setBorder(Rectangle.NO_BORDER);
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setBorderWidthTop(1);
            footer.setBorderWidthBottom(0);
            documento.setFooter(footer);  
            
            //abrindo o documento
            documento.open();   //abrindo o documento                                    
                        
            //definindo a quantidade de colunas do relatorio em 5 colunas (nome, email,pr, simproc, tid) com um array de floats chamado
            //colsWidth, sendo que cada elemento trata o tamanho de cada coluna
            float[] colsWidth = {1.8f, 4.3f, 1.1f, 1.1f, 0.8f};
            
            //criando a tabela com a qde e o tamanho das colunas definidas acima
            PdfPTable tabela     = new PdfPTable(colsWidth);
            
            //definindo a largura total da tabela
            tabela.setTotalWidth(550);
            tabela.setLockedWidth(true);   
            
            //adicionando o cabecalho das colunas ao documento
            tabela.addCell("Nome");
            tabela.addCell("Email");
            tabela.addCell("PR");
            tabela.addCell("Simproc");
            tabela.addCell("Sei");            
                        
            //mostrando os dados das colunas em suas celulas
            while(conexao.rs.next())
            {
                tabela.addCell(conexao.rs.getString("nome"));
                tabela.addCell(conexao.rs.getString("email"));
                tabela.addCell(conexao.rs.getString("pr"));
                tabela.addCell(conexao.rs.getString("simproc"));
                tabela.addCell(conexao.rs.getString("sei"));
            }  
                                  
            //adiciona a tabela ao documento
            documento.add(tabela); 
            
            //fechando o documento
            documento.close(); 
            
            //fechando a conexao
            conexao.desconectar();
         
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }catch (DocumentException ex) {
                Logger.getLogger(GerarPDF.class.getName()).log(Level.SEVERE, null, ex);
            }
         catch (SQLException ex) {
                Logger.getLogger(GerarPDF.class.getName()).log(Level.SEVERE, null, ex);
            }
         //abrindo o PDF depois de criado e mostrando na tela
        try {
            if(cont==1)Desktop.getDesktop().open(new File(caminhoDoPDF+".pdf"));
        } catch (IOException ex) {
            Logger.getLogger(GerarPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void GerarPDFCLIENTES()
    {        
        //Recebe o caminho do PDF
        getCaminhoPDF();    
        
        try {
            //conectando os dados
            conexao.conectar();
            sql = "SELECT c.nome, c.rf, s.nome as secao FROM tblClientes c, tblSecoes s WHERE s.codigo = c.secaoid ORDER BY c.nome";
            conexao.ExecutarPesquisaSQL(sql);
            
            //instanciando o documento com o relatorio nele       
            OutputStream relatorio = new FileOutputStream(caminhoDoPDF+".pdf");
            PdfWriter writer       = PdfWriter.getInstance(documento, relatorio);   
            
            // criando cabecalho obs:(cabeçalhos e rodapés devem ser adicionados antes que o documento seja aberto) false para ignorar o numero da pagina
            HeaderFooter header = new HeaderFooter(new Phrase("RELATÓRIO DE COLABORADORES ATIVOS", font), false);
            header.setBorder(Rectangle.NO_BORDER);
            header.setAlignment(Element.ALIGN_CENTER);
            documento.setHeader(header);  
                         
            // criando rodapé obs:(cabeçalhos e rodapés devem ser adicionados antes que o documento seja aberto) true para mostrar o numero da pagina
            HeaderFooter footer = new HeaderFooter(new Phrase("Página : ", font1), true);
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setBorderWidthTop(1);
            footer.setBorderWidthBottom(0);
            documento.setFooter(footer);  
            
            //abrindo o documento
            documento.open();   //abrindo o documento                                   
                      
            //definindo a quantidade de colunas do relatorio em 3 colunas (nome, rf, secao) com um array de floats chamado
            //colsWidth, sendo que cada elemento trata o tamanho de cada coluna
            float[] colsWidth = {3.9f, 0.6F, 1.3f};
            
            //criando a tabela com a qde e o tamanho das colunas definidas acima
            PdfPTable tabela     = new PdfPTable(colsWidth);
            
            //definindo a largura total da tabela
            tabela.setTotalWidth(550);
            tabela.setLockedWidth(true);   
                        
            //adicionando o cabecalho das colunas ao documento
            tabela.addCell("Nome");
            tabela.addCell("RF");
            tabela.addCell("Seção");
                        
            //mostrando os dados das colunas em suas celulas
            while(conexao.rs.next())
            {
                tabela.addCell(conexao.rs.getString("nome"));
                tabela.addCell(conexao.rs.getString("rf"));
                tabela.addCell(conexao.rs.getString("secao"));
            }  
                                  
            //adiciona a tabela ao documento
            documento.add(tabela); 
            
            //fechando o documento
            documento.close(); 
            
            //fechando a conexao
            conexao.desconectar();
         
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }catch (DocumentException ex) {
                Logger.getLogger(GerarPDF.class.getName()).log(Level.SEVERE, null, ex);
            }
         catch (SQLException ex) {
                Logger.getLogger(GerarPDF.class.getName()).log(Level.SEVERE, null, ex);
            }
         //abrindo o PDF depois de criado e mostrando na tela
        try {
            if(cont==1)Desktop.getDesktop().open(new File(caminhoDoPDF+".pdf"));
        } catch (IOException ex) {
            Logger.getLogger(GerarPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
}
    
public void GerarPDFCLIENTESINATIVADOS()
    {        
        //Recebe o caminho do PDF
        getCaminhoPDF();    
        
        try {
            //conectando os dados
            conexao.conectar();
            //sql = "SELECT c.nome, c.rf, s.nome as secao FROM tblClientes c, tblSecoes s WHERE s.codigo = c.secaoid and c.status = 'INATIVO' ORDER BY s.nome,c.nome";
            sql = "SELECT c.nome, c.rf, s.nome as secao FROM tblClientes c, tblSecoes s WHERE s.codigo = c.secaoid and c.status = 'INATIVO' ORDER BY c.nome";
            conexao.ExecutarPesquisaSQL(sql);
            
            //instanciando o documento com o relatorio nele       
            OutputStream relatorio = new FileOutputStream(caminhoDoPDF+".pdf");
            PdfWriter writer       = PdfWriter.getInstance(documento, relatorio);   
            
            // criando cabecalho obs:(cabeçalhos e rodapés devem ser adicionados antes que o documento seja aberto) false para ignorar o numero da pagina
            HeaderFooter header = new HeaderFooter(new Phrase("RELATÓRIO DE COLABORADORES INATIVOS", font), false);
            header.setBorder(Rectangle.NO_BORDER);
            header.setAlignment(Element.ALIGN_CENTER);
            documento.setHeader(header);  
                         
            // criando rodapé obs:(cabeçalhos e rodapés devem ser adicionados antes que o documento seja aberto) true para mostrar o numero da pagina
            HeaderFooter footer = new HeaderFooter(new Phrase("Página : ", font1), true);
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setBorderWidthTop(1);
            footer.setBorderWidthBottom(0);
            documento.setFooter(footer);  
            
            //abrindo o documento
            documento.open();   //abrindo o documento                                   
                      
            //definindo a quantidade de colunas do relatorio em 3 colunas (nome, rf, secao) com um array de floats chamado
            //colsWidth, sendo que cada elemento trata o tamanho de cada coluna
            float[] colsWidth = {3.9f, 0.6F, 1.3f};
            
            //criando a tabela com a qde e o tamanho das colunas definidas acima
            PdfPTable tabela     = new PdfPTable(colsWidth);
            
            //definindo a largura total da tabela
            tabela.setTotalWidth(550);
            tabela.setLockedWidth(true);   
                        
            //adicionando o cabecalho das colunas ao documento
            tabela.addCell("Nome");
            tabela.addCell("RF");
            tabela.addCell("Seção");
                        
            //mostrando os dados das colunas em suas celulas
            while(conexao.rs.next())
            {
                tabela.addCell(conexao.rs.getString("nome"));
                tabela.addCell(conexao.rs.getString("rf"));
                tabela.addCell(conexao.rs.getString("secao"));
            }  
                                  
            //adiciona a tabela ao documento
            documento.add(tabela); 
            
            //fechando o documento
            documento.close(); 
            
            //fechando a conexao
            conexao.desconectar();
         
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }catch (DocumentException ex) {
                Logger.getLogger(GerarPDF.class.getName()).log(Level.SEVERE, null, ex);
            }
         catch (SQLException ex) {
                Logger.getLogger(GerarPDF.class.getName()).log(Level.SEVERE, null, ex);
            }
         //abrindo o PDF depois de criado e mostrando na tela
        try {
            if(cont==1)Desktop.getDesktop().open(new File(caminhoDoPDF+".pdf"));
        } catch (IOException ex) {
            Logger.getLogger(GerarPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
}
    
public void GerarPDFUSUARIOS()
{        
        //Recebe o caminho do PDF
        getCaminhoPDF();    
        
        try {
            //conectando os dados
            conexao.conectar();
            sql = "SELECT u.nome, u.rf, s.nome as secao FROM tblUsuarios u, tblSecoes s WHERE u.status='ATIVO' and s.codigo = u.secaoid ORDER BY u.nome";
            conexao.ExecutarPesquisaSQL(sql);
            
            //instanciando o documento com o relatorio nele       
            OutputStream relatorio = new FileOutputStream(caminhoDoPDF+".pdf");
            PdfWriter writer       = PdfWriter.getInstance(documento, relatorio);   
            
            // criando cabecalho obs:(cabeçalhos e rodapés devem ser adicionados antes que o documento seja aberto) false para ignorar o numero da pagina
            HeaderFooter header = new HeaderFooter(new Phrase("RELATÓRIO DE USUÁRIOS ATIVOS", font), false);
            header.setBorder(Rectangle.NO_BORDER);
            header.setAlignment(Element.ALIGN_CENTER);
            documento.setHeader(header);  
                         
            // criando rodapé obs:(cabeçalhos e rodapés devem ser adicionados antes que o documento seja aberto) true para mostrar o numero da pagina
            HeaderFooter footer = new HeaderFooter(new Phrase("Página : ", font1), true);
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setBorderWidthTop(1);
            footer.setBorderWidthBottom(0);
            documento.setFooter(footer);  
            
            //abrindo o documento
            documento.open();   //abrindo o documento                                   
                      
            //definindo a quantidade de colunas do relatorio em 3 colunas (nome, rf, secao) com um array de floats chamado
            //colsWidth, sendo que cada elemento trata o tamanho de cada coluna
            float[] colsWidth = {3.9f, 0.6F, 1.3f};
            
            //criando a tabela com a qde e o tamanho das colunas definidas acima
            PdfPTable tabela     = new PdfPTable(colsWidth);
            
            //definindo a largura total da tabela
            tabela.setTotalWidth(550);
            tabela.setLockedWidth(true);   
                        
            //adicionando o cabecalho das colunas ao documento
            tabela.addCell("Nome");
            tabela.addCell("RF");
            tabela.addCell("Seção");
                        
            //mostrando os dados das colunas em suas celulas
            while(conexao.rs.next())
            {
                tabela.addCell(conexao.rs.getString("nome"));
                tabela.addCell(conexao.rs.getString("rf"));
                tabela.addCell(conexao.rs.getString("secao"));
            }  
                                  
            //adiciona a tabela ao documento
            documento.add(tabela); 
            
            //fechando o documento
            documento.close(); 
            
            //fechando a conexao
            conexao.desconectar();
         
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }catch (DocumentException ex) {
                Logger.getLogger(GerarPDF.class.getName()).log(Level.SEVERE, null, ex);
            }
         catch (SQLException ex) {
                Logger.getLogger(GerarPDF.class.getName()).log(Level.SEVERE, null, ex);
            }
         //abrindo o PDF depois de criado e mostrando na tela
        try {
            if(cont==1)Desktop.getDesktop().open(new File(caminhoDoPDF+".pdf"));
        } catch (IOException ex) {
            Logger.getLogger(GerarPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
} 

public void GerarPDFUSUARIOSINATIVOS()
{        
        //Recebe o caminho do PDF
        getCaminhoPDF();    
        
        try {
            //conectando os dados
            conexao.conectar();
            sql = "SELECT u.nome, u.rf, s.nome as secao FROM tblUsuarios u, tblSecoes s WHERE u.status='INATIVO' and s.codigo = u.secaoid ORDER BY u.nome";
            conexao.ExecutarPesquisaSQL(sql);
            
            //instanciando o documento com o relatorio nele       
            OutputStream relatorio = new FileOutputStream(caminhoDoPDF+".pdf");
            PdfWriter writer       = PdfWriter.getInstance(documento, relatorio);   
            
            // criando cabecalho obs:(cabeçalhos e rodapés devem ser adicionados antes que o documento seja aberto) false para ignorar o numero da pagina
            HeaderFooter header = new HeaderFooter(new Phrase("RELATÓRIO DE USUÁRIOS INATIVOS", font), false);
            header.setBorder(Rectangle.NO_BORDER);
            header.setAlignment(Element.ALIGN_CENTER);
            documento.setHeader(header);  
                         
            // criando rodapé obs:(cabeçalhos e rodapés devem ser adicionados antes que o documento seja aberto) true para mostrar o numero da pagina
            HeaderFooter footer = new HeaderFooter(new Phrase("Página : ", font1), true);
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setBorderWidthTop(1);
            footer.setBorderWidthBottom(0);
            documento.setFooter(footer);  
            
            //abrindo o documento
            documento.open();   //abrindo o documento                                   
                      
            //definindo a quantidade de colunas do relatorio em 3 colunas (nome, rf, secao) com um array de floats chamado
            //colsWidth, sendo que cada elemento trata o tamanho de cada coluna
            float[] colsWidth = {3.9f, 0.6F, 1.3f};
            
            //criando a tabela com a qde e o tamanho das colunas definidas acima
            PdfPTable tabela     = new PdfPTable(colsWidth);
            
            //definindo a largura total da tabela
            tabela.setTotalWidth(550);
            tabela.setLockedWidth(true);   
                        
            //adicionando o cabecalho das colunas ao documento
            tabela.addCell("Nome");
            tabela.addCell("RF");
            tabela.addCell("Seção");
                        
            //mostrando os dados das colunas em suas celulas
            while(conexao.rs.next())
            {
                tabela.addCell(conexao.rs.getString("nome"));
                tabela.addCell(conexao.rs.getString("rf"));
                tabela.addCell(conexao.rs.getString("secao"));
            }  
                                  
            //adiciona a tabela ao documento
            documento.add(tabela); 
            
            //fechando o documento
            documento.close(); 
            
            //fechando a conexao
            conexao.desconectar();
         
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }catch (DocumentException ex) {
                Logger.getLogger(GerarPDF.class.getName()).log(Level.SEVERE, null, ex);
            }
         catch (SQLException ex) {
                Logger.getLogger(GerarPDF.class.getName()).log(Level.SEVERE, null, ex);
            }
         //abrindo o PDF depois de criado e mostrando na tela
        try {
            if(cont==1)Desktop.getDesktop().open(new File(caminhoDoPDF+".pdf"));
        } catch (IOException ex) {
            Logger.getLogger(GerarPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
} 

public void GerarPDFCONTATOS()
{        
        //Recebe o caminho do PDF
        getCaminhoPDF();    
        
        try {
            //conectando os dados
            conexao.conectar();
            sql = "SELECT nome, telefone, celular, departamento FROM tblContatos ORDER BY nome";
            conexao.ExecutarPesquisaSQL(sql);
            
            //instanciando o documento com o relatorio nele       
            OutputStream relatorio = new FileOutputStream(caminhoDoPDF+".pdf");
            PdfWriter writer       = PdfWriter.getInstance(documento, relatorio);   
            
            // criando cabecalho obs:(cabeçalhos e rodapés devem ser adicionados antes que o documento seja aberto) false para ignorar o numero da pagina
            HeaderFooter header = new HeaderFooter(new Phrase("RELATÓRIO DE CONTATOS", font), false);
            header.setBorder(Rectangle.NO_BORDER);
            header.setAlignment(Element.ALIGN_CENTER);
            documento.setHeader(header);  
                         
            // criando rodapé obs:(cabeçalhos e rodapés devem ser adicionados antes que o documento seja aberto) true para mostrar o numero da pagina
            HeaderFooter footer = new HeaderFooter(new Phrase("Página : ", font1), true);
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setBorderWidthTop(1);
            footer.setBorderWidthBottom(0);
            documento.setFooter(footer);  
            
            //abrindo o documento
            documento.open();   //abrindo o documento                                   
                      
            //definindo a quantidade de colunas do relatorio em 3 colunas (nome, rf, secao) com um array de floats chamado
            //colsWidth, sendo que cada elemento trata o tamanho de cada coluna
            float[] colsWidth = {3.2f, 0.8f, 0.9f, 1.6f};
            
            //criando a tabela com a qde e o tamanho das colunas definidas acima
            PdfPTable tabela     = new PdfPTable(colsWidth);
            
            //definindo a largura total da tabela
            tabela.setTotalWidth(550);
            tabela.setLockedWidth(true);   
                        
            //adicionando o cabecalho das colunas ao documento
            tabela.addCell("Nome");
            tabela.addCell("Telefone");
            tabela.addCell("Celular");
            tabela.addCell("Departamento");
                        
            //mostrando os dados das colunas em suas celulas
            while(conexao.rs.next())
            {
                tabela.addCell(conexao.rs.getString("nome"));
                tabela.addCell(conexao.rs.getString("telefone"));
                tabela.addCell(conexao.rs.getString("celular"));
                tabela.addCell(conexao.rs.getString("departamento"));
            }  
                                  
            //adiciona a tabela ao documento
            documento.add(tabela); 
            
            //fechando o documento
            documento.close(); 
            
            //fechando a conexao
            conexao.desconectar();
         
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }catch (DocumentException ex) {
                Logger.getLogger(GerarPDF.class.getName()).log(Level.SEVERE, null, ex);
            }
         catch (SQLException ex) {
                Logger.getLogger(GerarPDF.class.getName()).log(Level.SEVERE, null, ex);
            }
         //abrindo o PDF depois de criado e mostrando na tela
        try {
            if(cont==1)Desktop.getDesktop().open(new File(caminhoDoPDF+".pdf"));
        } catch (IOException ex) {
            Logger.getLogger(GerarPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
}    

public void GerarPDFPATRIMOVEIS()
{        
        //Recebe o caminho do PDF
        getCaminhoPDF();    
        
        try {
            //conectando os dados
            conexao.conectar();
            sql = "SELECT p.serial, p.chapa, p.modeloid, p.status, m.modelo FROM tblpatrimovel p, tblmodelos m WHERE p.modeloid = m.codigo and p.status='ATIVO' ORDER by m.modelo";
            conexao.ExecutarPesquisaSQL(sql);
            
            //instanciando o documento com o relatorio nele       
            OutputStream relatorio = new FileOutputStream(caminhoDoPDF+".pdf");
            PdfWriter writer       = PdfWriter.getInstance(documento, relatorio);   
            
            // criando cabecalho obs:(cabeçalhos e rodapés devem ser adicionados antes que o documento seja aberto) false para ignorar o numero da pagina
            HeaderFooter header = new HeaderFooter(new Phrase("RELATÓRIO DE PATRIMÔNIOS MÓVEIS", font), false);
            header.setBorder(Rectangle.NO_BORDER);
            header.setAlignment(Element.ALIGN_CENTER);
            documento.setHeader(header);  
                         
            // criando rodapé obs:(cabeçalhos e rodapés devem ser adicionados antes que o documento seja aberto) true para mostrar o numero da pagina
            HeaderFooter footer = new HeaderFooter(new Phrase("Página : ", font1), true);
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setBorderWidthTop(1);
            footer.setBorderWidthBottom(0);
            documento.setFooter(footer);  
            
            //abrindo o documento
            documento.open();   //abrindo o documento                                   
                      
            //definindo a quantidade de colunas do relatorio em 3 colunas (nome, rf, secao) com um array de floats chamado
            //colsWidth, sendo que cada elemento trata o tamanho de cada coluna
            float[] colsWidth = {4.2f, 1.9f, 1.9f };
            
            //criando a tabela com a qde e o tamanho das colunas definidas acima
            PdfPTable tabela     = new PdfPTable(colsWidth);
            
            //definindo a largura total da tabela
            tabela.setTotalWidth(550);
            tabela.setLockedWidth(true);   
                        
            //adicionando o cabecalho das colunas ao documento
            tabela.addCell("Descrição");
            tabela.addCell("Série");
            tabela.addCell("Chapa");
            
                        
            //mostrando os dados das colunas em suas celulas
            while(conexao.rs.next())
            {
                tabela.addCell(conexao.rs.getString("modelo"));
                tabela.addCell(conexao.rs.getString("serial"));
                tabela.addCell(conexao.rs.getString("chapa"));
                
            }  
                                  
            //adiciona a tabela ao documento
            documento.add(tabela); 
            
            //fechando o documento
            documento.close(); 
            
            //fechando a conexao
            conexao.desconectar();
         
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }catch (DocumentException ex) {
                Logger.getLogger(GerarPDF.class.getName()).log(Level.SEVERE, null, ex);
            }
         catch (SQLException ex) {
                Logger.getLogger(GerarPDF.class.getName()).log(Level.SEVERE, null, ex);
            }
         //abrindo o PDF depois de criado e mostrando na tela
        try {
            if(cont==1)Desktop.getDesktop().open(new File(caminhoDoPDF+".pdf"));
        } catch (IOException ex) {
            Logger.getLogger(GerarPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
}   
    
public static void headerFooter()
{
        //Baixei este exemplo para aprendizado e como colocar um rodape na pagina
    
        // creation of the document with a certain size and certain margins
        // (you can use PageSize.Letter instead of PageSize.A4)
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        try
        {
            // creation of the different writers
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("c:/relatorio/iTextExample.pdf"));

            // various fonts
            BaseFont bf_helv = BaseFont.createFont(BaseFont.HELVETICA, "Cp1252", false);
            BaseFont bf_times = BaseFont.createFont(BaseFont.TIMES_ROMAN, "Cp1252", false);
            BaseFont bf_courier = BaseFont.createFont(BaseFont.COURIER, "Cp1252", false);
            BaseFont bf_symbol = BaseFont.createFont(BaseFont.SYMBOL, "Cp1252", false);

            // headers and footers must be added before the document is opened
            HeaderFooter footer = new HeaderFooter(
                        new Phrase("Esta é a página: ", new Font(bf_courier)), true);
            footer.setBorder(Rectangle.NO_BORDER);
            footer.setAlignment(Element.ALIGN_CENTER);
            document.setFooter(footer);

            HeaderFooter header = new HeaderFooter(
                        new Phrase("Este é um cabeçalho sem um número de página", new Font(bf_symbol)), false);
            header.setAlignment(Element.ALIGN_CENTER);
            document.setHeader(header);

            document.open();

            int y_line1 = 650;
            int y_line2 = y_line1 - 50;
            int y_line3 = y_line2 - 50;

            // draw a few lines ...
            PdfContentByte cb = writer.getDirectContent();
            cb.setLineWidth(0f);
            cb.moveTo(250, y_line3 - 100);
            cb.lineTo(250, y_line1 + 100);
            cb.moveTo(50, y_line1);
            cb.lineTo(400, y_line1);
            cb.moveTo(50, y_line2);
            cb.lineTo(400, y_line2);
            cb.moveTo(50, y_line3);
            cb.lineTo(400, y_line3);
            cb.stroke();
            // ... and some text that is aligned in various ways
            cb.beginText();
            cb.setFontAndSize(bf_helv, 12);
            String text = "Sample text for alignment";
            cb.showTextAligned(PdfContentByte.ALIGN_CENTER, text + " Center", 250, y_line1, 0);
            cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, text + " Right", 250, y_line2, 0);
            cb.showTextAligned(PdfContentByte.ALIGN_LEFT, text + " Left", 250, y_line3, 0);
            cb.endText();

            // start second page
            document.newPage();

            // add text in three paragraphs from top to bottom with various font styles
            Paragraph par = new Paragraph("bold paragraph");
            par.getFont().setStyle(Font.BOLD);
            document.add(par);
            par = new Paragraph("italic paragraph");
            par.getFont().setStyle(Font.ITALIC);
            document.add(par);
            par = new Paragraph("underlined and strike-through paragraph");
            par.getFont().setStyle(Font.UNDERLINE | Font.STRIKETHRU);
            document.add(par);

            // demonstrate some table features
            Table table = new Table(3);
                // 2 pixel wide blue border
            table.setBorderWidth(2);
            table.setBorderColor(new Color(0, 0, 255));
            table.setPadding(5);
            table.setSpacing(5);
            Cell c = new Cell("header");
            c.setHeader(true);
            c.setColspan(3);
            table.addCell(c);
            table.endHeaders();
            c = new Cell("example cell with rowspan 2 and red border");
            c.setRowspan(2);
            c.setBorderColor(new Color(255, 0, 0));
            table.addCell(c);
            table.addCell("1.1");
            table.addCell("2.1");
            table.addCell("1.2");
            table.addCell("2.2");
            c = new Cell("align center");
            c.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c);
            c = new Cell("big cell");
            c.setRowspan(2);
            c.setColspan(2);
            table.addCell(c);
            c = new Cell("align right");
            c.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(c);
            document.add(table);

            // add text at an absolute position
            cb.beginText();
            cb.setFontAndSize(bf_times, 14);
            cb.setTextMatrix(100, 300);
            cb.showText("Text at position 100, 300.");
            cb.endText();

            // rotated text at an absolute position
            PdfTemplate template = cb.createTemplate(300, 300);                
            template.beginText();
            template.setFontAndSize(bf_times, 14);
            template.showText("Rotated text at position 400, 200.");
            template.endText();

            float rotate = 90;
            float x = 400;
            float y = 200;
            float angle  = (float) (-rotate * (Math.PI / 180));
            float xScale = (float) Math.cos(angle);
            float yScale = (float) Math.cos(angle);
            float xRot   = (float) -Math.sin(angle);
            float yRot   = (float) Math.sin(angle);

            cb.addTemplate(template, xScale, xRot, yRot, yScale, x, y);

            // we're done!
            document.close();

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    		
}
    
}
