package snake.mcmods.theinvoker.energy;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class EnergyContainer
{
    public EnergyContainer(TileEntity te, boolean isEnergyProvider, int containerEnergyID)
    {
        if (te == null)
            throw new IllegalArgumentException("you dare to create a EnergyContainer without a valid TileEntity?");
        this.te = te;
        this.isEnergyProvider = isEnergyProvider;
        if (EnergyCenter.INSTANCE.getEnergyForce(containerEnergyID) != null)
            this.energyID = containerEnergyID;
        else
            throw new IllegalArgumentException("you have created a EnergyContainer without a valid Energy ID");
        isAvailable=true;
    }

    protected int effectiveRange;
    protected int maxEnergyRequst;
    protected int capacity;
    protected boolean isEnergyProvider;
    protected int energyLevel;
    private TileEntity te;
    private int energyID;
    private boolean isAvailable;
    private boolean isRegistered;

    public boolean getIsRegistered()
    {
        return isRegistered;
    }

    public void setEffectiveRange(int range)
    {
        effectiveRange = range;
    }

    public int getEffectiveRange()
    {
        return effectiveRange;
    }

    public int gain(int energyFlow, boolean doGain)
    {
        int tobeGain = energyFlow;
        if (energyLevel + tobeGain > capacity)
        {
            tobeGain = capacity - energyLevel;
        }
        if (doGain)
        {
            energyLevel += tobeGain;
            EnergyUtils.fireEvent(new EnergyContainerEvent.EnergyGainedEvent(te.worldObj, this, te.xCoord, te.yCoord, te.zCoord, tobeGain));
        }
        return tobeGain;
    }

    public int take(int energyFlow, boolean doTake)
    {
        int tobeTake = energyFlow;
        if (energyLevel - energyFlow < 0)
        {
            tobeTake = energyLevel;
        }
        if (doTake)
        {
            energyLevel -= tobeTake;
            EnergyUtils.fireEvent(new EnergyContainerEvent.EnergyTookEvent(te.worldObj, this, te.xCoord, te.yCoord, te.zCoord, tobeTake));
        }
        return tobeTake;
    }

    public int getEnergyCapacity()
    {
        return capacity;
    }

    public void setEnergyCapacity(int capacity)
    {
        this.capacity = capacity;
        if (energyLevel > this.capacity)
            energyLevel = capacity;
    }

    public int getEnergyLevel()
    {
        return energyLevel;
    }

    public void setEnergyLevel(int level)
    {
        energyLevel = level;
    }

    public int getMaxEnergyRequest()
    {
        return maxEnergyRequst;
    }

    public void setMaxEnergyRequest(int val)
    {
        maxEnergyRequst = val;
    }

    public boolean getIsAvailable()
    {
        return isAvailable;
    }

    public void setIsAvailable(boolean val)
    {
        isAvailable = val;
    }

    public boolean getIsEnergyProvider()
    {
        return isEnergyProvider;
    }

    public TileEntity getTileEntity()
    {
        return te;
    }

    public int getContainerEnergyID()
    {
        return energyID;
    }

    public void register()
    {
        if (!isRegistered&&this.te.worldObj!=null&&!this.te.worldObj.isRemote)
            isRegistered = EnergyCenter.INSTANCE.registerContainer(this);
    }

    public void destroy()
    {
        EnergyCenter.INSTANCE.removeContainer(this);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        nbt.setInteger(TAG_RANGE, getEffectiveRange());
        nbt.setInteger(TAG_MAX_REQUEST, getMaxEnergyRequest());
        nbt.setInteger(TAG_CAPACITY, getEnergyCapacity());
        nbt.setInteger(TAG_ENERGY_LEVEL, getEnergyLevel());
        nbt.setInteger(TAG_ENERGY_ID, getContainerEnergyID());
        nbt.setBoolean(TAG_IS_PROVIDER, getIsEnergyProvider());
        nbt.setBoolean(TAG_IS_AVAILABLE, getIsAvailable());
        return nbt;
    }

    public static EnergyContainer readFromNBT(NBTTagCompound nbt, TileEntity te)
    {
        EnergyContainer c = new EnergyContainer(te, nbt.getBoolean(TAG_IS_PROVIDER), nbt.getInteger(TAG_ENERGY_ID));
        if (nbt.hasKey(TAG_RANGE))
            c.setEffectiveRange(nbt.getInteger(TAG_RANGE));
        if (nbt.hasKey(TAG_MAX_REQUEST))
            c.setMaxEnergyRequest(nbt.getInteger(TAG_MAX_REQUEST));
        if (nbt.hasKey(TAG_CAPACITY))
            c.setEnergyCapacity(nbt.getInteger(TAG_CAPACITY));
        if (nbt.hasKey(TAG_ENERGY_LEVEL))
            c.setEnergyLevel(nbt.getInteger(TAG_ENERGY_LEVEL));
        if (nbt.hasKey(TAG_IS_AVAILABLE))
            c.setIsAvailable(nbt.getBoolean(TAG_IS_AVAILABLE));
        return c;
    }
    
    private static final String TAG_RANGE = "range";
    private static final String TAG_MAX_REQUEST = "maxRequest";
    private static final String TAG_CAPACITY = "capacity";
    private static final String TAG_ENERGY_LEVEL = "energyLevel";
    private static final String TAG_ENERGY_ID = "energyId";
    private static final String TAG_IS_PROVIDER = "isProvider";
    private static final String TAG_IS_AVAILABLE = "isAvailable";
}
