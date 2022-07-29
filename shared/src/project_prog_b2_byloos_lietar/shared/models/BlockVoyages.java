package project_prog_b2_byloos_lietar.shared.models;

public class BlockVoyages extends Serial{
    boolean shoulBlock;

    public BlockVoyages(boolean shouldBlock){
        this.shoulBlock = shouldBlock;
    }

    public boolean isShoulBlock() {
        return shoulBlock;
    }
}
