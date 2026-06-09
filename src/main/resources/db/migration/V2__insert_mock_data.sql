INSERT INTO app_users (name, email, password, role) VALUES
('Administrador FIAP', 'admin@fiap.com', '$2a$10$wO1SrNaYkvULi65Eab05z.WQLTLGDfB4z4nQC8pNnPKZBMKyWvJ3y', 'ADMIN'),
('Usuario Demonstracao', 'user@fiap.com', '$2a$10$wO1SrNaYkvULi65Eab05z.WQLTLGDfB4z4nQC8pNnPKZBMKyWvJ3y', 'USER');

INSERT INTO monitored_regions
(name, city, state, neighborhood, latitude, longitude, river_name, risk_level, active, description)
VALUES
('Marginal Tiete - Ponte das Bandeiras', 'Sao Paulo', 'SP', 'Bom Retiro', -23.5219000, -46.6322000, 'Rio Tiete', 'HIGH', TRUE, 'Regiao urbana com historico de alagamentos em periodos de chuva intensa.'),
('Centro de Franco da Rocha', 'Franco da Rocha', 'SP', 'Centro', -23.3229000, -46.7290000, 'Ribeirao Eusébio', 'CRITICAL', TRUE, 'Area proxima a curso d''agua e com risco recorrente de enchente.'),
('Avenida Brasil - Iraja', 'Rio de Janeiro', 'RJ', 'Iraja', -22.8273000, -43.3321000, 'Rio Iraja', 'MEDIUM', TRUE, 'Trecho com pontos de alagamento em eventos de chuva moderada.'),
('Bairro Industrial', 'Contagem', 'MG', 'Industrial', -19.9368000, -44.0539000, 'Ribeirao Arrudas', 'LOW', TRUE, 'Regiao monitorada preventivamente para planejamento de infraestrutura.');

INSERT INTO risk_analysis_records
(region_id, analyzed_at, rainfall_mm, temperature, humidity, external_condition, calculated_risk, recommendation)
VALUES
(1, TIMESTAMP '2026-06-03 09:00:00', 34.50, 21.80, 88, 'Chuva moderada', 'HIGH', 'Monitorar vias de acesso, comunicar equipes locais e acompanhar atualizacoes de chuva.'),
(2, TIMESTAMP '2026-06-03 09:10:00', 55.20, 20.10, 94, 'Chuva forte', 'CRITICAL', 'Acionar plano de contingencia, orientar a populacao e priorizar areas proxima ao rio.');

INSERT INTO flood_alerts
(region_id, title, message, severity, status, created_at, resolved_at)
VALUES
(1, 'Risco alto de enchente', 'A analise inicial indicou risco alto para a regiao Marginal Tiete - Ponte das Bandeiras.', 'WARNING', 'OPEN', TIMESTAMP '2026-06-03 09:01:00', NULL),
(2, 'Risco critico de enchente', 'A analise inicial indicou risco critico para o Centro de Franco da Rocha.', 'CRITICAL', 'OPEN', TIMESTAMP '2026-06-03 09:11:00', NULL);
