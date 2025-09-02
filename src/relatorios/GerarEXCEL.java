package relatorios;

import static biblioteca.VariaveisPublicas.lstListaCampos;
import conexao.ConnConexao;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import static biblioteca.VariaveisPublicas.qdeColunas;
import java.sql.Connection;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/*
        Para uso coloque o codigo abaixo em um evento de botao para escolher os campos que farão parte do relatorio
        lstListaCampos.clear();
        F_SELECIONARCAMPOS frm = new F_SELECIONARCAMPOS(new javax.swing.JFrame(), true);
        frm.setVisible(true);
*/

public class GerarEXCEL {
        
     ConnConexao conexao    = new ConnConexao();
     Connection  conn       = null;   
     String nomePlanilha    = null;
     
    //funcao publica, que recebe um ArrayList de objetos com os dados vindo do banco
    public ArrayList<Object[]> getListaDados(String sql){
        //conectando ao banco de dados
        conn = conexao.conectar();  
        
        //criando o objeto ArrayList para receber os valores e retornar os valores para funcao getListaDados
        ArrayList<Object[]> tableDataList = null;
        
        //verificando se a conexao não for nula selecionar os dados do banco atribuindo os valores ao ArrayList tableDataList
        if(conexao != null){
            try{
                //faz o mapeamento do que dever ser atribuido ao relatorio
                PreparedStatement pst = conn.prepareStatement(sql);
                ResultSet result     = pst.executeQuery();
                tableDataList        = new ArrayList();
                
                while(result.next()){
                    Object[] objArray = new Object[qdeColunas]; //aqui vai a qde de campos que deseja no relatorio do excel
                    
                    for(int i=0; i<qdeColunas; i++){
                        objArray[i] = result.getString(i+1);
                    }
                    
                    //atribuindo o objArray a tableDataList
                    tableDataList.add(objArray);                    
                    
                }
            }catch(SQLException e){
                e.printStackTrace();
                System.out.println("Não é possível criar o PreparedStatement");
            }
        }
        return tableDataList;
    }
     
    public void doExportar(ArrayList<Object[]> dataLista){
        //configurando o arquivo do excel
        if(dataLista != null && !dataLista.isEmpty()){
            // Criando area de trabalho para o excel
            HSSFWorkbook workBook = new HSSFWorkbook();
            
            //criando uma nova planilha
            HSSFSheet sheet  = workBook.createSheet();
            
            //atribuindo o nome da planilha retirando os 3 primeiros caracteres ou seja de TBLCARGOS ficou CARGOS            
            //nomePlanilha = tabela.substring(3);
            //workBook.setSheetName(0, nomePlanilha);
            workBook.setSheetName(0, "RELATORIO DE DOCUMENTOS");
                        
            //criando uma linha para o cabecalho
            HSSFRow headingRow = sheet.createRow(0);
                     
           //gerando os nomes das colunas do cabeçalho
           for(int i=0; i<qdeColunas; i++){
                headingRow.createCell((int)i).setCellValue(lstListaCampos.get(i)); 
           }
                       
            //atribuindo os dados vindo do banco as celulas do arquivo do excel
            short rowNo = 1;
            for (Object[] objects : dataLista) {
                HSSFRow row = sheet.createRow(rowNo);
                
                for(int i=0; i<qdeColunas; i++){
                    row.createCell((int)i).setCellValue(objects[i].toString());
                }                
                rowNo++;                
            }
            
            //depois de setar os dados aqui eu determino que as colunas sejam abertas com o tamanho dos textos
            for(int i=0; i<qdeColunas; i++){
                sheet.autoSizeColumn(i, true);
            }
            
            //abrindo opção para selecionar o local para salvar o arquivo
            JFileChooser chooser            = new JFileChooser();
            FileNameExtensionFilter filtro  = new FileNameExtensionFilter("Arquivos do excel","xls");
            chooser.setFileFilter(filtro);
            chooser.setDialogTitle("Salvar arquivo");
            chooser.setMultiSelectionEnabled(false);
            chooser.setAcceptAllFileFilterUsed(false);
        
        //se escolheu um local aceitavel continua
        if(chooser.showSaveDialog(null)==JFileChooser.APPROVE_OPTION){
            try{
                String file          = chooser.getSelectedFile().toString().concat(".xls");
                FileOutputStream fos = new FileOutputStream(file);
                workBook.write(fos);
                fos.flush();
                try {
                     //abrindo automaticamente o arquivo gerado
                     Desktop.getDesktop().open(new File(file));
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null,"Não foi possível abrir o arquivo excel!");
                }
            }catch(FileNotFoundException e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,"Atenção o diretório não foi encontrado,operação cancelada!","Diretório inválido!",2);
            }catch(IOException e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,"Ocorreu um erro ao tentar gravar o arquivo,operação cancelada!","Erro Fatal!",2);
            }
          }else{
            JOptionPane.showMessageDialog(null,"Processo cancelado pelo usuário!","Cancelado!",1);
          }
      }
    }  
}