/*Esta classe gera um relatorio no Excel dos dados da tabela conforme a sql passada*/
package biblioteca;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import modelo.Tipo;
import Dao.DAOTipo;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

public class GerarRelParaExcel {
    Biblioteca umabiblio = new Biblioteca();
    DAOTipo tipoDAO      = new DAOTipo();  
    String nomeCampo     = null;
    int qdeCampos        = 0;
    
    public void GerarExcel(String tabela, String sql){
        //recuperando a qde de colunas da tabela    
        qdeCampos     = umabiblio.retornaQdeColunasDaTabela(tabela);     
            
        //recuperando os nomes das colunas da tabela
        ArrayList<String> lstNomesColunas = umabiblio.recuperarNomesColunas(tabela);

        //se quiser visualizar os nomes das colunas retornados
//        for(String nmColuna:lstNomesColunas){
//            System.out.println(nmColuna);
//        }

        //selecionar o local para salvar o arquivo
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Arquivos de excel", "xls");
        chooser.setFileFilter(filtro);
        chooser.setDialogTitle("Salvar arquivo");
        chooser.setMultiSelectionEnabled(false);

        //se escolheu um local valido
        if(chooser.showSaveDialog(null)==JFileChooser.APPROVE_OPTION){
            //passando o caminho completo do nome do arquivo a ser gerado
            String nomeArquivo = chooser.getSelectedFile().toString().concat(".xls");

//            try 
            {  
                List<Tipo> lstTipos  = tipoDAO.gerarListaTiposSQL(sql);                

                //criando o cabecalho
                HSSFWorkbook workbook   = new HSSFWorkbook();
                HSSFSheet sheet         = workbook.createSheet("Lista de Tipos"); //nome da aba a ser cridada
                Row cabecalho           = sheet.createRow(0);

                for(int i=0; i < qdeCampos; i++){
                    //passando os nomes das colunas para o cabeçalho atraves dos nomes retornados pelo array
                    nomeCampo = lstNomesColunas.get(i);
                    cabecalho.createCell(i).setCellValue(nomeCampo);                                        

                    //formatando o cabeçalho
                    HSSFCellStyle estiloCabecalho = workbook.createCellStyle();
                    HSSFFont font = workbook.createFont();
                    font.setFontName(HSSFFont.FONT_ARIAL);
                    font.setFontHeightInPoints((short)10);
                    estiloCabecalho.setFont(font);
                    estiloCabecalho.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER);
                    cabecalho.getCell(i).setCellStyle(estiloCabecalho);

                }


            //para começar na segunda linha depois do cabecalho
            int r = 1;

                //FALTANDO AUTOMATIZAR A CRIAÇÃO DAS CELULAS SOMENTE

                    //iterando a lista e criando as celulas o arquivo excel
//                    for(Tipo objTipo : lstTipos){
//                          Row row = sheet.createRow(r++);
//                          for(int i=0; i < qdeCampos; i++){
//                              //allGetters foi criado no modelo da classe Tipo para que sejam gerados todos os gets de todos os atributos como acima
//                              Object[] objetos = objTipo.allGetters();   
//                              Cell cell = (Cell) row.createCell(i);
//                              cell.setCellValue(objetos[i].toString());                           
//                          }

                        r++;


                    for(int i=0; i < qdeCampos; i++){
                        //tamanho das celulas automaticas     
                        sheet.autoSizeColumn(i);
                    }

                }

                //salvar o arquivo excel
//                FileOutputStream out = new FileOutputStream(new File(nomeArquivo));
//                workbook.write(out);
//                out.close();
//                JOptionPane.showMessageDialog(null, "Arquivo Excel gerado com sucesso!");
//
//                } catch (Exception ex) {
//                    System.out.println(ex.getMessage());
//                }
//            }
        }   
        }
}


/*
private void GerarParaExcel() {
        //ESTE METODO FOI GERADO UTILIZANDO A BIBLIOTECA POI MAS COM ELE VOCE PRECISARÁ ITERAR TODOS OS CAMPOS MANUALMENTE
        String nomeCampo = null;
        
        //recuperando a qde de colunas da tabela
        int qdeCampos = 0;
        qdeCampos     = umabiblio.retornaQdeColunasDaTabela("tbltipos");
        
        //recuperando os nomes das colunas da tabela
        ArrayList<String> lstNomesColunas = umabiblio.recuperarNomesColunas("tbltipos");
        
        //se quiser visualizar os nomes das colunas retornados
//        for(String nmColuna:lstNomesColunas){
//            System.out.println(nmColuna);
//        }
        
        //selecionar o local para salvar o arquivo
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Arquivos de excel", "xls");
        chooser.setFileFilter(filtro);
        chooser.setDialogTitle("Salvar arquivo");
        chooser.setMultiSelectionEnabled(false);
               
        //se escolheu um local valido
        if(chooser.showSaveDialog(null)==JFileChooser.APPROVE_OPTION){
            //passando o caminho completo do nome do arquivo a ser gerado
            String nomeArquivo = chooser.getSelectedFile().toString().concat(".xls");
            
            try 
            {  
                List<Tipo> lstTipos = tipoDAO.gerarListaTiposSQL(sqlDefault);                
                
                //criando o cabecalho
                HSSFWorkbook workbook   = new HSSFWorkbook();
                HSSFSheet sheet         = workbook.createSheet("Lista de Tipos"); //nome da aba a ser cridada
                Row cabecalho           = sheet.createRow(0);
                
                for(int i=0; i < qdeCampos; i++){
                    //passando os nomes das colunas para o cabeçalho atraves dos nomes retornados pelo array
                    nomeCampo = lstNomesColunas.get(i);
                    cabecalho.createCell(i).setCellValue(nomeCampo);                                        
                    
                    //formatando o cabeçalho
                    HSSFCellStyle estiloCabecalho = workbook.createCellStyle();
                    HSSFFont font = workbook.createFont();
                    font.setFontName(HSSFFont.FONT_ARIAL);
                    font.setFontHeightInPoints((short)10);
                    estiloCabecalho.setFont(font);
                    estiloCabecalho.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER);
                    cabecalho.getCell(i).setCellStyle(estiloCabecalho);
                   
                }
                
                
            //para começar na segunda linha depois do cabecalho
            int r = 1;
            
            //FALTANDO AUTOMATIZAR A CRIAÇÃO DAS CELULAS SOMENTE
            
                //iterando a lista e criando as celulas o arquivo excel
                for(Tipo objTipo : lstTipos){

                    Row row = sheet.createRow(r++);
                    
//                    //codigo
//                    Cell cellCodigo = row.createCell(0);
//                    cellCodigo.setCellValue(objTipo.getCodigo());
//
//                    //nome
//                    Cell cellTipo = row.createCell(1);
//                    cellTipo.setCellValue(objTipo.getTipo());
//
//                    //secao
//                    Cell cellStatus = row.createCell(2);
//                    cellStatus.setCellValue(objTipo.getStatus());
//                      
                      
                      for(int i=0; i < qdeCampos; i++){
                          //allGetters foi criado no modelo da classe Tipo para que sejam gerados todos os gets de todos os atributos como acima
                          Object[] objetos = objTipo.allGetters();   
                          Cell cell = row.createCell(i);
                          cell.setCellValue((String) objetos[i].toString());                           
                      }

                    r++;
              
            
                for(int i=0; i < qdeCampos; i++){
                    //tamanho das celulas automaticas     
                    sheet.autoSizeColumn(i);
                }
               
            }
            
            //salvar o arquivo excel
            FileOutputStream out = new FileOutputStream(new File(nomeArquivo));
            workbook.write(out);
            out.close();
            JOptionPane.showMessageDialog(null, "Arquivo Excel gerado com sucesso!");

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
*/