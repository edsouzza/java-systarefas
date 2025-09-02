/*
 * Exporta dados de uma nomeJTable no java para um arquivo excel
 */
package biblioteca;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import javax.swing.JTable;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class ExportarExcel {

    private final File arquivo;
    private final List<JTable> nomeJTable;
    private final List<String> nomePlanilha;

    public ExportarExcel(File arquivo, List<JTable> nomeJTable, List<String> nomePlanilha) throws Exception {
        this.arquivo        = arquivo;
        this.nomeJTable     = nomeJTable;
        this.nomePlanilha   = nomePlanilha;

        if (nomePlanilha.size() != nomeJTable.size()) {
            throw new Exception("Error");
        }
    }
    
//    public void gerarCabecalho(){
//        // Cabeçalho
//        String cabecalho[] = new String[5];
//        cabecalho[0] = "CODIGO";
//        cabecalho[1] = "TIPO";
//        cabecalho[2] = "STATUS";
//
//        // Write the Header to the excel file
//        for (int k = 0; k < cabecalho.length; k++) {
//                Label label = new Label(0, 0, cabecalho[k]);
//                s.addCell(label);
//                WritableCell cell = s.getWritableCell(k, 0);
//
//        }
//        
//    }

    public boolean exportar() 
    {
        try {
            DataOutputStream out = new DataOutputStream(new FileOutputStream(arquivo));
            WritableWorkbook w = Workbook.createWorkbook(out);
            
            for (int index = 0; index < nomeJTable.size(); index++) {
                JTable tbl = nomeJTable.get(index);
                WritableSheet s = w.createSheet(nomePlanilha.get(index), 0);     
                
                for (int i = 0; i < tbl.getColumnCount(); i++) {
                    for (int j = 0; j < tbl.getRowCount(); j++) {
                        Object object = tbl.getValueAt(j, i);
                        s.addCell(new Label(i, j, String.valueOf(object)));
                        
                        //define o tamanho das colunas (coluna, tamanho) = Obs: o tamanho esta fixo em 30 caracteres o ideal seria automatico
                        s.setColumnView(0, 5); 
                        s.setColumnView(i, 30); 
                        
                    }
                    
                }
            }
            w.write();
            w.close();
            return true;
        } catch (IOException | WriteException e) {
            return false;
        }
    }    
        
}
