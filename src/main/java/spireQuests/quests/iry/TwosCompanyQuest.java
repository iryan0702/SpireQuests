package spireQuests.quests.iry;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import spireQuests.quests.AbstractQuest;
import spireQuests.quests.QuestReward;
import spireQuests.quests.Trigger;
import spireQuests.quests.iry.relics.BrokenHeartPendant;

// TwosCompanyQuest:
// Have 2 enemies alive on your final turn of combat for 3 combats
// Rewards the Broken Heart Pendant
public class TwosCompanyQuest extends AbstractQuest {
    public static final Trigger<Void> TWOS_COMPANY = new Trigger<>();

    public TwosCompanyQuest() {
        super(QuestType.SHORT, QuestDifficulty.NORMAL);

        new TriggerTracker<>(TWOS_COMPANY, 3).add(this);

        AbstractRelic rewardRelic = new BrokenHeartPendant();
        addReward(new QuestReward.RelicReward(rewardRelic));

        useDefaultReward = false;
    }

    @Override
    public boolean canSpawn() {
        return (AbstractDungeon.actNum == 1 || AbstractDungeon.actNum == 2) && AbstractDungeon.floorNum > 0;
    }
}
