package solver.optimiztor;

import solver.model.Map;


public class ChooseWayTest {

    public void testGetNext() {
        OptimizationContext optimizationContext = new OptimizationContext();
        getTestMap();
    }


    /**
     * Тестовая карта
     * D  - 1 - 2
     * |    |
     * 3 -  4 - 5
     * |    |
     * 6    7

     */
    private void getTestMap() {
        Map map = new Map();
        map.addPoint(0,0);      //D
        map.addPoint(10, 0);    //1
        map.addPoint(25, 0);    //2

        map.addPoint(0, 5);     //3
        map.addPoint(10, 5);    //4
        map.addPoint(30, 5);    //5

        map.addPoint(0, 15);    //6
        map.addPoint(10, 15);   //7

    }
}