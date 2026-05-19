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