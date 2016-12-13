package net.infernalrealms.blacksmithing;

import java.util.Random;

public final class HelmetNameGenerator {

	private static final Random RANDOM = new Random();
	private static final String[] nm1 = new String[] { "Ancient", "Binding", "Blessed", "Blind", "Broken", "Burning", "Condemned",
			"Conquered", "Cursed", "Damned", "Dark", "Demonic", "Distant", "Divine", "Doomed", "Ending", "Endless", "Eternal", "Faded",
			"Fallen", "Fleeting", "Frozen", "Hallowed", "Haunted", "Hellish", "Holy", "Imminent", "Immortal", "Infernal", "Infinite",
			"Lost", "Ominous", "Relentless", "Sacred", "Shattered", "Silent", "Smoldering", "Timeless", "Twisted", "Unholy" };
	private static final String[] nm2 = new String[] { "Ancestors", "Bloodlust", "Comrades", "Damnation", "Dreams", "Fire", "Fires",
			"Fortune", "Fortunes", "Freedom", "Glory", "Hell", "Hells", "Honor", "Hope", "Illusions", "Justice", "Kings", "Lands", "Magic",
			"Memories", "Might", "Misery", "Nightmares", "Nights", "Power", "Powers", "Protection", "Punishment", "Sorrow", "Souls",
			"Torment", "Trials", "Vengeance", "Visions", "Voices", "Warlords", "Wars", "Whispers", "Worlds" };
	private static final String[] nm3 = new String[] { "Ancient", "Arcane", "Atuned", "Bandit's", "Baneful", "Banished", "Barbarian",
			"Barbaric", "Battleworn", "Blood Infused", "Blood-Forged", "Bloodcursed", "Bloodied", "Bloodlord's", "Bloodsurge", "Brutal",
			"Brutality", "Burnished", "Captain's", "Cataclysm", "Cataclysmic", "Challenger", "Challenger's", "Champion", "Champion's",
			"Cold-Forged", "Conqueror", "Conqueror's", "Corrupted", "Crazed", "Crying", "Cursed", "Defender", "Defender's", "Defiled",
			"Demonic", "Desire's", "Desolation", "Destiny's", "Dire", "Doom", "Doom's", "Dragon's", "Dragon", "Ebon", "Enchanted",
			"Engraved", "Eternal", "Exile", "Extinction", "Faith's", "Faithful", "Fearful", "Feral", "Fierce", "Fiery", "Fire Infused",
			"Firesoul", "Forsaken", "Fortune's", "Frenzied", "Frost", "Frozen", "Furious", "Fusion", "Ghastly", "Ghost-Forged", "Ghostly",
			"Gladiator", "Gladiator's", "Grieving", "Guard's", "Guardian's", "Hatred", "Haunted", "Heartless", "Hero", "Hero's", "Hollow",
			"Holy", "Honed", "Honor's", "Hope's", "Hopeless", "Howling", "Hungering", "Incarnated", "Infused", "Inherited", "Jade Infused",
			"Judgement", "Keeper's", "Knightly", "Legionnaire's", "Liar's", "Lich", "Lightning", "Lonely", "Loyal", "Lusting", "Malevolent",
			"Malicious", "Malignant", "Massive", "Mended", "Mercenary", "Military", "Misfortune's", "Mourning", "Nightmare", "Oathkeeper's",
			"Ominous", "Peacekeeper", "Peacekeeper's", "Phantom", "Possessed", "Pride's", "Primal", "Prime", "Primitive", "Promised",
			"Protector's", "Proud", "Recruit's", "Reforged", "Reincarnated", "Relentless", "Remorse", "Renewed", "Renovated", "Restored",
			"Retribution", "Ritual", "Roaring", "Ruby Infused", "Rune-Forged", "Savage", "Sentinel", "Shadow", "Silent", "Singing",
			"Sinister", "Soldier's", "Solitude's", "Sorrow's", "Soul", "Soul Infused", "Soul-Forged", "Soulless", "Spectral", "Spite",
			"Storm", "Storm-Forged", "Stormfury", "Stormguard", "Terror", "Thunder", "Thunder-Forged", "Thunderfury", "Thunderguard",
			"Thundersoul", "Thunderstorm", "Timeworn", "Tormented", "Trainee's", "Treachery's", "Twilight", "Twilight's", "Twisted",
			"Tyrannical", "Undead", "Unholy", "Vanquisher", "Vengeance", "Vengeful", "Vicious", "Victor", "Vindication", "Vindicator",
			"Vindictive", "War-Forged", "Warden's", "Warlord's", "Warped", "Warrior", "Warrior's", "Whistling", "Wicked", "Wind's",
			"Wind-Forged", "Windsong", "Woeful", "Wrathful", "Wretched", "Yearning", "Zealous", "", "", "", "", "", "", "", "", "" };
	private static final String[] nm4 = new String[] { "Adamantite", "Plate", "Scaled", "Bone", "Bronze", "Bronzed", "Ivory", "Ebon",
			"Golden", "Iron", "Mithril", "Obsidian", "Silver", "Skeletal", "Steel", "Titanium", "Demon" };
	private static final String[] nm4_2 = new String[] { "Cloth", "Heavy Hide", "Heavy Leather", "Hide", "Leather", "Linen", "Padded",
			"Quilted", "Rugged Leather", "Scaled", "Silk", "Wool" };
	private static final String[] nm5 = new String[] { "Casque", "Crown", "Faceguard", "Gaze", "Greathelm", "Headcover", "Headguard",
			"Helm", "Helmet", "Jaws", "Visage" };
	private static final String[] nm5_2 = new String[] { "Bandana", "Cap", "Coif", "Cowl", "Facemask", "Headguard", "Headpiece", "Helm",
			"Hood", "Mask" };
	private static final String[] nm6_2 = new String[] { "Armament", "Bandana", "Bane", "Birth", "Blessing", "Blight", "Bond", "Boon",
			"Burden", "Call", "Cap", "Champion", "Coif", "Conqueror", "Cowl", "Crusader", "Cry", "Curator", "Dawn", "Death", "Defender",
			"Defiler", "Deflector", "Demise", "Destroyer", "Edge", "Emissary", "End", "Ender", "Facemask", "Fall", "Favor", "Foe",
			"Fortune", "Gift", "Glory", "Guard", "Guardian", "Headguard", "Headpiece", "Heirloom", "Helm", "Hero", "Hood", "Hope",
			"Incarnation", "Keeper", "Last Hope", "Last Stand", "Legacy", "Mask", "Memory", "Might", "Oath", "Pact", "Pledge", "Promise",
			"Protection", "Protector", "Reach", "Shepherd", "Soul", "Steward", "Terror", "Token", "Tribute", "Vengeance", "Vindicator",
			"Visage", "Voice", "Ward", "Warden", "Whisper", "Wit" };
	private static final String[] nm6 = new String[] { "Armament", "Bane", "Birth", "Blessing", "Blight", "Bond", "Boon", "Burden", "Call",
			"Casque", "Champion", "Conqueror", "Crown", "Crusader", "Cry", "Curator", "Dawn", "Death", "Defender", "Defiler", "Deflector",
			"Demise", "Destroyer", "Edge", "Emissary", "End", "Ender", "Faceguard", "Fall", "Favor", "Foe", "Fortune", "Gaze", "Gift",
			"Glory", "Greathelm", "Guard", "Guardian", "Headcover", "Headguard", "Heirloom", "Helm", "Helmet", "Hero", "Hope",
			"Incarnation", "Jaws", "Keeper", "Last Hope", "Last Stand", "Legacy", "Memory", "Might", "Oath", "Pact", "Pledge", "Promise",
			"Protection", "Protector", "Reach", "Shepherd", "Soul", "Steward", "Terror", "Token", "Tribute", "Vengeance", "Vindicator",
			"Visage", "Voice", "Ward", "Warden", "Whisper", "Wit" };
	private static final String[] nm7 = new String[] { "Absorption", "the Phoenix", "Adventure", "Agony", "Ancient Power", "Ancient Powers",
			"Anger", "Anguish", "Annihilation", "Arcane Magic", "Arcane Power", "Arcane Resist", "Archery", "Ashes", "Assassination",
			"Assassins", "Assaults", "Auras", "Awareness", "Barriers", "Beginnings", "Binding", "Black Magic", "Blast Protection",
			"Blessed Fortune", "Blessed Fortunes", "Blessings", "Blight", "Blood", "Bloodlust", "Bloodshed", "Bravery", "Broken Bones",
			"Broken Dreams", "Broken Families", "Broken Worlds", "Burdens", "Carnage", "Cataclysms", "Chaos", "Clarity", "Conquered Worlds",
			"Corruption", "Courage", "Creation", "Cunning", "Danger", "Dark Magic", "Dark Powers", "Dark Souls", "Darkness", "Dawn",
			"Decay", "Deception", "Defiance", "Deflection", "Delirium", "Delusions", "Demon Fire", "Demons", "Denial", "Desecration",
			"Despair", "Destruction", "Devotion", "Diligence", "Discipline", "Dishonor", "Dismay", "Dominance", "Domination", "Doom",
			"Dragons", "Dragonsouls", "Dread", "Dreams", "Due Diligence", "Duels", "Dusk", "Echoes", "Enchantments", "Ended Dreams",
			"Ending Hope", "Ending Misery", "Ends", "Eternal Bloodlust", "Eternal Damnation", "Eternal Glory", "Eternal Justice",
			"Eternal Rest", "Eternal Sorrow", "Eternal Struggles", "Eternity", "Executions", "Extinction", "Faded Memories", "Fallen Kings",
			"Fallen Souls", "Fire", "Fire Magic", "Fire Power", "Fire Protection", "Fire Resist", "Fools", "Forging", "Fortitude",
			"Fortune", "Frost", "Frost Power", "Frost Resist", "Frozen Hells", "Fury", "Ghosts", "Giants", "Giantslaying", "Glory", "Grace",
			"Greed", "Grieving Widows", "Guardians", "Hate", "Hatred", "Healing", "Hell", "Hell's Games", "Hellfire", "Hellish Torment",
			"Heroes", "Holy Might", "Honor", "Hope", "Horrors", "Ice", "Ice Magic", "Illusions", "Immortality", "Inception",
			"Infinite Trials", "Infinity", "Insanity", "Justice", "Kings", "Life", "Lifemending", "Lifestealing", "Light's Hope", "Limbo",
			"Lost Comrades", "Lost Hope", "Lost Souls", "Lost Voices", "Lost Worlds", "Mercy", "Might", "Miracles", "Misery", "Mists",
			"Moonlight", "Mysteries", "Mystery", "Nature", "Necromancy", "Nightmares", "Oblivion", "Paradise", "Patience", "Phantoms",
			"Power", "Prayers", "Pride", "Pride's Fall", "Prophecies", "Protection", "Putrefaction", "Reckoning", "Recoil", "Redemption",
			"Regret", "Regrets", "Resilience", "Respect", "Riddles", "Ruins", "Runes", "Salvation", "Secrecy", "Secrets", "Serenity",
			"Shadows", "Shifting Sands", "Silence", "Slaughter", "Slaying", "Smite", "Solitude", "Souls", "Stealth", "Stone", "Storms",
			"Strength", "Subtlety", "Suffering", "Suffering's End", "Sunfire", "Sunlight", "Swordbreaking", "Tears", "Terror", "Terrors",
			"Thieves", "Thorns", "Thunder", "Thunders", "Titans", "Torment", "Traitors", "Trust", "Truth", "Truths", "Twilight",
			"Twilight's End", "Twisted Visions", "Undoing", "Unholy Blight", "Unholy Might", "Valiance", "Valor", "Vengeance", "Vigor",
			"Visions", "War", "Whispers", "Wisdom", "Woe", "Wonders", "Wraiths", "Zeal", "the Ancients", "the Archer", "the Banished",
			"the Basilisk", "the Bear", "the Beast", "the Berserker", "the Blessed", "the Boar", "the Breaking Storm", "the Brotherhood",
			"the Burning Sun", "the Caged Mind", "the Cataclysm", "the Champion", "the Claw", "the Corrupted", "the Covenant", "the Crown",
			"the Crusader", "the Damned", "the Day", "the Daywalker", "the Dead", "the Depth", "the Depths", "the Dragons", "the Dreadlord",
			"the Eagle", "the Earth", "the East", "the Eclipse", "the Emperor", "the End", "the Enigma", "the Fallen", "the Falling Sky",
			"the Flames", "the Forest", "the Forests", "the Forgotten", "the Forsaken", "the Gargoyle", "the Gladiator", "the Gods",
			"the Harvest", "the Hunter", "the Immortal", "the Immortals", "the Incoming Storm", "the Insane", "the Isles", "the King",
			"the Knight", "the Lasting Night", "the Leviathan", "the Light", "the Lion", "the Lionheart", "the Lone Victor",
			"the Lone Wolf", "the Lost", "the Mage", "the Moon", "the Moonwalker", "the Mountain", "the Mountains", "the Night",
			"the Night Sky", "the Nightstalker", "the North", "the Occult", "the Oracle", "the Phoenix", "the Plague", "the Prince",
			"the Princess", "the Prisoner", "the Prodigy", "the Prophecy", "the Prophet", "the Protector", "the Queen", "the Scourge",
			"the Seer", "the Serpent", "the Setting Sun", "the Shadows", "the Sky", "the South", "the Stars", "the Steward", "the Storm",
			"the Summoner", "the Sun", "the Sunwalker", "the Swamp", "the Talon", "the Titans", "the Undying", "the Victor", "the Void",
			"the Volcano", "the Ward", "the Warrior", "the West", "the Whale", "the Whispers", "the Wicked", "the Wind", "the Wolf",
			"the World", "the Wretched" };

	private HelmetNameGenerator() {}

	public static String generateName(boolean heavy) {
		String name;
		int roll = RANDOM.nextInt(5);
		int rnd;
		int rnd2;
		int rnd3;
		int rnd4;
		String[] nm4;
		String[] nm5;
		String[] nm6;
		if (heavy) {
			nm4 = HelmetNameGenerator.nm4;
			nm5 = HelmetNameGenerator.nm5;
			nm6 = HelmetNameGenerator.nm6;
		} else {
			nm4 = nm4_2;
			nm5 = nm5_2;
			nm6 = nm6_2;
		}
		switch (roll) {
		case 0:
			rnd = (int) Math.floor(Math.random() * nm1.length);
			rnd2 = (int) Math.floor(Math.random() * nm2.length);
			rnd3 = (int) Math.floor(Math.random() * nm5.length);
			name = nm5[rnd3] + " of " + nm1[rnd] + " " + nm2[rnd2];
			break;
		case 1:
			rnd = (int) Math.floor(Math.random() * nm1.length);
			rnd2 = (int) Math.floor(Math.random() * nm2.length);
			rnd3 = (int) Math.floor(Math.random() * nm4.length);
			rnd4 = (int) Math.floor(Math.random() * nm5.length);
			name = nm4[rnd3] + " " + nm5[rnd4] + " of " + nm1[rnd] + " " + nm2[rnd2];
			break;
		case 2:
			rnd = (int) Math.floor(Math.random() * nm3.length);
			rnd2 = (int) Math.floor(Math.random() * nm4.length);
			rnd3 = (int) Math.floor(Math.random() * nm5.length);
			name = nm3[rnd] + " " + nm4[rnd2] + " " + nm5[rnd3];
			break;
		case 3:
			rnd = (int) Math.floor(Math.random() * nm3.length);
			rnd2 = (int) Math.floor(Math.random() * nm5.length);
			rnd3 = (int) Math.floor(Math.random() * nm7.length);
			name = nm3[rnd] + " " + nm5[rnd2] + " of " + nm7[rnd3];
			break;
		case 4:
			rnd = (int) Math.floor(Math.random() * nm6.length);
			rnd2 = (int) Math.floor(Math.random() * nm7.length);
			name = nm6[rnd] + " of " + nm7[rnd2];
			break;
		default:
			name = null;
		}
		return name;
	}

}
