package spireQuests.quests.dayvig;

import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Courier;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import com.megacrit.cardcrawl.rooms.ShopRoom;
import spireQuests.patches.QuestTriggers;
import spireQuests.quests.AbstractQuest;
import spireQuests.quests.QuestReward;
import spireQuests.quests.iry.util.LessonQuestUtil;
import spireQuests.util.Wiz;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CowardiceQuest extends AbstractQuest {
    public CowardiceQuest() {
        super(QuestType.SHORT, QuestDifficulty.NORMAL);


        new TriggerTracker<>(QuestTriggers.LEAVE_ROOM, 3)
                .triggerCondition(this::dodgedElite)
                .add(this);

        addReward(new QuestReward.RelicReward(AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.COMMON)));
    }

    public boolean dodgedElite(MapRoomNode currNode){
        boolean connectedToElite = false;
        for (MapRoomNode m : AbstractDungeon.map.get(currNode.y + 1)){
            if (m.getRoom() != null && currNode.isConnectedTo(m) && m.getRoom() instanceof MonsterRoomElite && m != AbstractDungeon.nextRoom){
                connectedToElite = true;
                break;
            }
        }
        return connectedToElite && !(AbstractDungeon.nextRoom instanceof MonsterRoomElite);
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.actNum < 3;
    }
}
