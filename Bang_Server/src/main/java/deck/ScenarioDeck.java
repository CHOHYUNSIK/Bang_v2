/* 
 * class name: ScenarioDeck
 * purpose: game scenario card deck
 */

package deck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import card.ScenarioCard;

public class ScenarioDeck{
	// deck
	private List<ScenarioCard> deck;

	// constructor
	// last_scenario == 1 (fistful of cards) last_scenario == 2 (high noon)
	public ScenarioDeck(){
		deck = new ArrayList<>();
	}

	// making original deck
	// num == 1 (fistful of cards & high noon) num == 2 (wild west show)
	public void make_init_deck(int num, boolean isFistful){
		if(num == 1){
			// re-init scenario deck
			deck = new ArrayList<>();
			// add senario cards
			// a_fistful_of_cards
			deck.add(new ScenarioCard("a_fistful_of_cards","agguato"));
			deck.add(new ScenarioCard("a_fistful_of_cards","cecchino"));
			deck.add(new ScenarioCard("a_fistful_of_cards","dead_man"));
			deck.add(new ScenarioCard("a_fistful_of_cards","fratelli_di_sangue"));
			deck.add(new ScenarioCard("a_fistful_of_cards","il_giudice"));
			deck.add(new ScenarioCard("a_fistful_of_cards","lazo"));
			deck.add(new ScenarioCard("a_fistful_of_cards","legge_del_west"));
			deck.add(new ScenarioCard("a_fistful_of_cards","liquore_forte"));
			deck.add(new ScenarioCard("a_fistful_of_cards","miniera_abbandonata"));
			deck.add(new ScenarioCard("a_fistful_of_cards","peyote"));
			deck.add(new ScenarioCard("a_fistful_of_cards","ranch"));
			deck.add(new ScenarioCard("a_fistful_of_cards","rimbalzo"));
			deck.add(new ScenarioCard("a_fistful_of_cards","roulette_russa"));
			deck.add(new ScenarioCard("a_fistful_of_cards","vendetta"));
			// high_noon
			deck.add(new ScenarioCard("high_noon","benedizione"));
			deck.add(new ScenarioCard("high_noon","citta'_fantasma"));
			deck.add(new ScenarioCard("high_noon","corsa_all'oro"));
			deck.add(new ScenarioCard("high_noon","i_dalton"));
			deck.add(new ScenarioCard("high_noon","il_dottore"));
			deck.add(new ScenarioCard("high_noon","il_reverendo"));
			deck.add(new ScenarioCard("high_noon","il_treno"));
			deck.add(new ScenarioCard("high_noon","maledizione"));
			deck.add(new ScenarioCard("high_noon","sbornia"));
			deck.add(new ScenarioCard("high_noon","sermone"));
			deck.add(new ScenarioCard("high_noon","sete"));
			deck.add(new ScenarioCard("high_noon","sparatoria"));
			// remove scenarioCard until remain 11 cards
			remove_cards(11);
			// shuffle deck
			shuffle();
			// add last scenario card
			if(isFistful == true) deck.add(deck.size(), new ScenarioCard("a_fistful_of_cards","per_un_pugno_di_carte"));
			else deck.add(deck.size(), new ScenarioCard("high_noon","mezzogiorno_di_fuoco"));
		}
		else if(num == 2){
			// re-init scenario deck
			deck = new ArrayList<>();
			// add scenario cards
			// wild_west_show
			deck.add(new ScenarioCard("wild_west_show","bavaglio"));
			deck.add(new ScenarioCard("wild_west_show","camposanto"));
			deck.add(new ScenarioCard("wild_west_show","darling_valentine"));
			deck.add(new ScenarioCard("wild_west_show","dorothy_rage"));
			deck.add(new ScenarioCard("wild_west_show","helena_zontero"));
			deck.add(new ScenarioCard("wild_west_show","lady_rosa_del_texas"));
			deck.add(new ScenarioCard("wild_west_show","miss_susanna"));
			deck.add(new ScenarioCard("wild_west_show","regolamento_di_conti"));
			deck.add(new ScenarioCard("wild_west_show","sacagaway"));
			// shuffle deck
			shuffle();
			// add last scenario card
			deck.add(deck.size(), new ScenarioCard("wild_west_show","wild_west_show"));
		}
	}

	// remove cards until [num] left
	private void remove_cards(int num){
		while(deck.size() > num){
			// make random number (0~deck.size()-1)
			int randomNumber = (int)(Math.random() * deck.size()-1);
			// System.out.println("[ScenarioDeck][RandomNumber]: "+randomNumber);
			// remove deck[randomNumber]
			// System.out.println("[ScenarioDeck][RandomNumber]: delete -> "+deck.get(randomNumber).getCardName());
			deck.remove(randomNumber);
		}
	}

	// getSize
	public int getSize(){
		return deck.size();
	}

	// shuffle role deck
	public void shuffle(){
		Collections.shuffle(deck);
	}

	// get [idx] element name (== cardName)
	public String getCardName(int idx){
		return deck.get(idx).getCardName();
	}
	
	// get [idx] element extension (== cardExtension)
	public String getCardExtension(int idx){
		return deck.get(idx).getCardExtension();
	}
}
