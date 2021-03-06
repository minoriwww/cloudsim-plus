package org.cloudbus.cloudsim.builders;

import java.util.List;
import org.cloudbus.cloudsim.brokers.DatacenterBrokerSimple;

/**
 * <p>A class that implements the Decorator Design Pattern in order to 
 * include functionalities in a existing class.
 * It is used to ensure that specific methods are called only after
 * a given method is called.</p>
 * 
 * For instance, the methods {@link #getVmBuilderForTheCreatedBroker()} and
 * {@link #getCloudletBuilderForTheCreatedBroker()} can only be called after
 * some {@link DatacenterBrokerSimple} was created by calling
 * the method {@link #createBroker()}.<br>
 * By this way, after the method is called, it returns
 * an instance of this decorator that allow
 * chained call to the specific decorator methods
 * as the following example:
 * <ul><li>{@link #createBroker() createBroker()}.{@link #getVmBuilderForTheCreatedBroker() getVmBuilderForTheCreatedBroker()}</li></ul>
 * 
 * @author Manoel Campos da Silva Filho
 */
public class BrokerBuilderDecorator implements BrokerBuilderInterface {
    private final BrokerBuilder builder;
    private final VmBuilder vmBuilder;
    private final CloudletBuilder cloudletBuilder;

    public BrokerBuilderDecorator(final BrokerBuilder builder, final DatacenterBrokerSimple broker) {
        if(builder == null)
           throw new RuntimeException("The builder parameter cannot be null."); 
        if(broker == null)
           throw new RuntimeException("The broker parameter cannot be null."); 
        this.builder = builder;
        
        this.vmBuilder = new VmBuilder(broker);        
        this.cloudletBuilder = new CloudletBuilder(this, broker);        
    }
    
    @Override
    public BrokerBuilderDecorator createBroker() {
        return builder.createBroker();
    }

    @Override
    public DatacenterBrokerSimple findBroker(int id) throws RuntimeException {
        return builder.findBroker(id);
    }

    @Override
    public List<DatacenterBrokerSimple> getBrokers() {
        return builder.getBrokers();
    }

    @Override
    public DatacenterBrokerSimple get(int index) {
       return builder.get(index);
    }
    
    /**
     * Gets the VM Builder in charge of creating VMs
     * to the {@link #broker} passed to this BrokerBuilderDecorator
     * @return 
     */
    public VmBuilder getVmBuilderForTheCreatedBroker() {
        return vmBuilder;
    }

    /**
     * Gets the Cloudlet Builder in charge of creating Cloudlets
     * to the {@link #broker} passed to this BrokerBuilderDecorator
     * @return 
     */
    public CloudletBuilder getCloudletBuilderForTheCreatedBroker() {
        return cloudletBuilder;
    }

}
