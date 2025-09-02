package relatorios;

import biblioteca.MetodosPublicos;
import static biblioteca.VariaveisPublicas.lstListaCampos;
import static biblioteca.VariaveisPublicas.qdeColunas;
import conexao.ConnConexao;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter; 
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/*
        Retorna todos os campos da tabela ideal quando tiver chaves estrangeiras na tabela você passa a SQL completa dos dados
        Importante salientar que não pode haver dados nulos nos registros
        Criada para gerar o EXCEL a partir de uma SQL, infelizmente não aceita os alias como nome de coluna
        Exemplo de uso no evento do botao ->
        GerarExcelTable excel = new GerarExcelTable();
        sql = "select f.codigo, s.nome as servidor, o.nome as orgao, f.ressarcimento, f.dataini, f.datafim, f.datadoc, f.status, f.obs "
            + "from "
            + "tblafastcomissionado f, tblservidores s, tblorgaos o "
            + "where "
            + "f.servidorid=s.codigo "
            + "and "
            + "f.orgaoid=o.codigo "
            + "order by s.nome";
        
        qdeColunas = umMetodo.retornarQdeColunasDaSql(sql);
        System.out.println("qde colunas..."+qdeColunas);

        //primeiro limpar a lista pois ela é utilizada por varias tabelas
        lstListaCampos.clear(); 
        umMetodo.preencherArrayListComCampos(lstListaCampos, sql);
        
        ArrayList<Object[]> dataList = excel.getListaDados(sql);
        if(dataList != null && dataList.size() > 0){
            excel.doExportar(dataList);
            JOptionPane.showMessageDialog(null,"O arquivo excel foi exportado com sucesso!");            
        }else{
            JOptionPane.showMessageDialog(null,"Não há dados disponível na tabela para exportar,operação cancelada!","Erro Fatal!",2);
        }
*/


public class GerarExcelTable {
        
     ConnConexao conexao  = new ConnConexao();
     Connection  conn     = null;   
     String             nomePlanilha = null;
     MetodosPublicos    umMetodo     = new MetodosPublicos();     
     int exportou = 0;
     
    //funcao publica que recebe um ArrayList com os dados vindo do banco listados na sql passada
    public ArrayList<Object[]> getListaDados(String sql){
        conn = conexao.conectar();    
        
        //criando o objeto ArrayList para receber os valores e retornar os valores para funcao getListaDados
        ArrayList<Object[]> tableDataList = null;
        
        //verificando se a conexao não for nula selecionar os dados do banco atribuindo os valores ao ArrayList tableDataList
        if(conexao != null){
            try{
                //identificar atraves da sql quantos campos e seus nomes
                PreparedStatement pst = conn.prepareStatement(sql);
                ResultSet result     = pst.executeQuery();
                tableDataList        = new ArrayList();
                
                while(result.next()){
                    Object[] objArray = new Object[qdeColunas]; //aqui vai a qde de campos que deseja no relatorio do excel
                    
                    for(int i=0; i<qdeColunas; i++){
                        objArray[i]  = result.getString(i+1);
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
     
    public void doExportar(ArrayList<Object[]> dataList){
        //configurando o arquivo do excel        
        if(dataList != null && !dataList.isEmpty()){
            // Criando area de trabalho para o excel
            HSSFWorkbook workBook = new HSSFWorkbook();
            
            //criando uma nova 
            HSSFSheet sheet       = workBook.createSheet();
            
            //atribuindo o nome da planilha retirando os 3 primeiros caracteres ou seja sem o TBL            
            //nomePlanilha = tabela.substring(3);
            //workBook.setSheetName(0, nomePlanilha);
            workBook.setSheetName(0, "DOCUMENTOS CADASTRADOS");
                        
            //criando a linha do cabecalho
            HSSFRow headingRow    = sheet.createRow(0);
            
            //gerando as celulas do cabecalho
            for(int i=0; i<qdeColunas; i++){
                headingRow.createCell((int)i).setCellValue(lstListaCampos.get(i)); 
            }
            
            //atribuindo os dados vindo do banco as celulas do arquivo do excel
            short rowNo = 1;
            for (Object[] objects : dataList) {
                HSSFRow row = sheet.createRow(rowNo);
                
                for(int i=0; i<qdeColunas; i++){
                    row.createCell((short)i).setCellValue(objects[i].toString());
                }                
                rowNo++;                
            }
                        
            //depois de setar os dados aqui eu determino que a coluna seja aberta no tamanho dos texto
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