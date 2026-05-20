const donnees = {
    "Dakar": ["Dakar", "Guédiawaye", "Pikine", "Rufisque"],
    "Thiès": ["Thiès", "Mbour", "Tivaouane"],
    "Diourbel": ["Diourbel", "Bambey", "Mbacké"],
    "Saint-Louis": ["Saint-Louis", "Dagana", "Podor"],
    "Louga": ["Louga", "Kébémer", "Linguère"],
    "Fatick": ["Fatick", "Foundiougne", "Gossas"],
    "Kaolack": ["Kaolack", "Nioro du Rip", "Guinguinéo"],
    "Kaffrine": ["Kaffrine", "Birkelane", "Koungheul", "Malem-Hodar"],
    "Tambacounda": ["Tambacounda", "Bakel", "Goudiry", "Koumpentoum"],
    "Kédougou": ["Kédougou", "Saraya", "Salemata"],
    "Kolda": ["Kolda", "Vélingara", "Médina Yoro Foulah"],
    "Ziguinchor": ["Ziguinchor", "Bignona", "Oussouye"],
    "Sédhiou": ["Sédhiou", "Bounkiling", "Goudomp"],
    "Matam": ["Matam", "Kanel", "Ranérou"]
};

function chargerDepartements() {
    const regionSelect = document.getElementById("region").value; // Récupère l'élément select pour les régions
    const departementSelect = document.getElementById("departement"); // Récupère l'élément select pour les départements
    departementSelect.innerHTML = '<option value="">--Sélectionnez un département--</option>'; // Réinitialise les options du select des départements

    if (regionSelect && donnees[regionSelect]) { // Vérifie si une région est sélectionnée et si des départements sont disponibles pour cette région
        donnees[regionSelect].forEach(dept => { // Parcourt la liste des départements pour la région sélectionnée
            const option = document.createElement("option"); // Crée un nouvel élément option pour chaque département
            option.value = dept;
            option.text = dept;
            departementSelect.appendChild(option);
        });
    }
}

// Compteur pour numéroter les localités
let compteur = 1;

// Fonction pour ajouter une nouvelle ligne localité
function ajouterLocalite() {
    compteur++;
    const container = document.getElementById("localites-container");
    
    const div = document.createElement("div");
    div.id = "localite-" + compteur;
    div.innerHTML = `
        <select name="regions" onchange="chargerDepartementsLigne(this, ${compteur})" required>
            <option value="">--Sélectionnez une région--</option>
            <option value="Dakar">Dakar</option>
            <option value="Thiès">Thiès</option>
            <option value="Diourbel">Diourbel</option>
            <option value="Saint-Louis">Saint-Louis</option>
            <option value="Louga">Louga</option>
            <option value="Fatick">Fatick</option>
            <option value="Kaolack">Kaolack</option>
            <option value="Kaffrine">Kaffrine</option>
            <option value="Tambacounda">Tambacounda</option>
            <option value="Kédougou">Kédougou</option>
            <option value="Kolda">Kolda</option>
            <option value="Ziguinchor">Ziguinchor</option>
            <option value="Sédhiou">Sédhiou</option>
            <option value="Matam">Matam</option>
        </select>

        <select name="departements" id="departement-${compteur}" required>
            <option value="">--Sélectionnez d'abord une région--</option>
        </select>

        <input type="text" name="communes" placeholder="Commune (optionnel)"/>

        <button type="button" onclick="supprimerLocalite(${compteur})">- Supprimer</button>
        <br><br>
    `;
    container.appendChild(div); // Ajoute la nouvelle ligne localité au conteneur des localités 
}

// Fonction pour charger les départements d'une ligne spécifique
function chargerDepartementsLigne(selectRegion, numero) {
    const region = selectRegion.value;
    const selectDept = document.getElementById("departement-" + numero);
    
    selectDept.innerHTML = '<option value="">--Sélectionnez un département--</option>';
    
    if (region && donnees[region]) {
        donnees[region].forEach(dept => {
            const option = document.createElement("option");
            option.value = dept;
            option.text = dept;
            selectDept.appendChild(option);
        });
    }
}

// Fonction pour supprimer une ligne localité
function supprimerLocalite(numero) {
    const div = document.getElementById("localite-" + numero);
    div.remove();
}