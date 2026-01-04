package spireQuests.quests.iry.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import javassist.CtBehavior;

import static spireQuests.quests.iry.TwosCompanyQuest.TWOS_COMPANY;

// TwosCompanyPatches:
// Tracks enemies alive every turn
// On victory, advances Two's Company quest if enemies alive was == 2
// (Patches are pretty much taken from QuestTriggers' patches)
public class TwosCompanyPatches {
    public static int enemiesAliveThisTurn = 0;

    @SpirePatch2(clz = GameActionManager.class, method = "getNextAction")
    @SpirePatch2(clz = AbstractRoom.class, method = "update")
    public static class OnTurnStart {
        @SpireInsertPatch(locator = Locator.class)
        public static void turnStartPatch() {
            enemiesAliveThisTurn = 0;
            for(AbstractMonster m2: AbstractDungeon.getCurrRoom().monsters.monsters){
                if (!m2.isDeadOrEscaped()) {
                    enemiesAliveThisTurn++;
                }
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "applyStartOfTurnRelics");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }

    @SpirePatch2(clz = AbstractPlayer.class, method = "onVictory")
    public static class OnCombatEndOrVictory {
        @SpirePrefixPatch
        public static void combatEndOrVictoryPatch() {
            if (CardCrawlGame.mode != CardCrawlGame.GameMode.GAMEPLAY) return;

            if (enemiesAliveThisTurn == 2) {
                TWOS_COMPANY.trigger();
            }
        }
    }
}
