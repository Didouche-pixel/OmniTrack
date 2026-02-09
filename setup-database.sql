-- ========================================
-- Script de création de la base OmniTrack
-- ========================================

-- 1. Créer la base de données (à exécuter dans postgres)
-- CREATE DATABASE omnitrack;

-- 2. Se connecter à omnitrack puis exécuter :

-- Créer la table parcours
CREATE TABLE IF NOT EXISTS parcours (
    id SERIAL PRIMARY KEY,
    client_id VARCHAR(100) NOT NULL,
    journey_type VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,
    channel VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    interrupted_at TIMESTAMP,
    resumed_at TIMESTAMP,
    completed_at TIMESTAMP
);

-- Données de test 
INSERT INTO parcours (client_id, journey_type, status, channel) VALUES
('CLIENT001', 'CREDIT', 'EN_COURS', 'WEB'),
('CLIENT002', 'EPARGNE', 'COMPLETED', 'MOBILE'),
('CLIENT003', 'CREDIT', 'INTERRUPTED', 'WEB'),
('CLIENT004', 'ASSURANCE', 'EN_COURS', 'AGENCE'),
('CLIENT005', 'CREDIT', 'COMPLETED', 'WEB');

-- Vérifier
SELECT * FROM parcours;
