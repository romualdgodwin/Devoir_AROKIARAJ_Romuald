package sio.devoir2graphiques.Tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class GraphiqueController
{
    private Connection cnx;
    private PreparedStatement ps;
    private ResultSet rs;

    public GraphiqueController() {
        cnx = ConnexionBDD.getCnx();
    }

    public HashMap<String,Integer> GetDatasGraphique1() {
        HashMap<String, Integer> datas = new HashMap();
        try {
            ps = cnx.prepareStatement("SELECT ageEmp,AVG(salaireEmp) AS moyenne FROM employe GROUP BY ageEmp; ");
            rs = ps.executeQuery();
            while (rs.next()) {
                datas.put(rs.getString("ageEmp"), rs.getInt("salaireEmp"));
            }
            ps.close();
            rs.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return datas;
    }
    public HashMap<String, ArrayList<String>> GetDatasGraphique2()
    {
        HashMap<String, ArrayList<String>> datas = new HashMap();
        try {
            ps = cnx.prepareStatement("SELECT ageEmp,'homme' as sexe, COUNT(*) as nb FROM employe WHERE sexe = 'homme' GROUP BY ageEmp UNION SELECT ageEmp,'femme' as sexe, COUNT(*) as nb FROM employe WHERE sexe = 'femme' GROUP BY ageEmp ORDER BY ageEmp, sexe;");
            rs = ps.executeQuery();
            while(rs.next())
            {
                if(!datas.containsKey(rs.getString("sexe")))
                {
                    ArrayList<String> leSexe = new ArrayList<>();
                    leSexe.add(rs.getString("tranche"));
                    leSexe.add(rs.getString("nb"));
                    datas.put(rs.getString("sexe"),leSexe);
                }
                else
                {
                    datas.get(rs.getString("sexe")).add(rs.getString("tranche"));
                    datas.get(rs.getString("sexe")).add(rs.getString("nb"));
                }
            }
            ps.close();
            rs.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return datas;
    }

        public HashMap<String,Integer> GetDatasGraphique3()
        {
            HashMap<String, Integer> datas = new HashMap();
            try {
                ps = cnx.prepareStatement("SELECT sexe, ROUND((COUNT(*) * 100.0) / (SELECT COUNT(*) FROM employe), 2) AS Pourcentage FROM employe GROUP BY sexe;");
                rs = ps.executeQuery();
                while(rs.next())
                {
                    datas.put(rs.getString("sexe"), rs.getInt("pourcentage"));
                }
                ps.close();
                rs.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            return datas;
    }


}
