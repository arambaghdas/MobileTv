package com.pttrackershared.models.eventbus;

/**
 * Converts CircuitJsonModel to Circuit
 */
public class CircuitJsonModelMapper {

    public Circuit convertToCircuit(CircuitJsonModel circuitJsonModel){
        Circuit circuit = new Circuit();
        if(circuitJsonModel.getCircuitId() != null)
            circuit.setCircuitId(circuitJsonModel.getCircuitId());
        if(circuitJsonModel.getTrainerId() != null)
            circuit.setTrainerId(circuitJsonModel.getTrainerId());
        if(circuitJsonModel.getCircuitname() != null)
            circuit.setName(circuitJsonModel.getCircuitname());
        if(circuitJsonModel.getIs_internal()!=null)
            circuit.setIs_internal(circuitJsonModel.getIs_internal());

        return circuit;
    }
}

