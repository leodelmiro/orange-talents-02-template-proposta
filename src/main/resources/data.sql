INSERT INTO tb_proposals(id, document, email, name, address, number, cep, salary, status, created_at)
VALUES (1, '45523947032', 'teste@teste.com', 'Testador', 'Rua dos Testes', '30', '12345-000', '2000', 'ELIGIBLE', '2021-03-10T08:47:08.644');

INSERT INTO tb_proposals(id, document, email, name, address, number, cep, salary, status, created_at)
VALUES (2, '71354141016', 'email@email.com', 'Email', 'Rua dos Testes', '30', '12345-000', '2000', 'ELIGIBLE', '2021-03-10T08:47:08.644');

INSERT INTO tb_proposals(id, document, email, name, address, number, cep, salary, status, created_at)
VALUES (3, '25737316009', 'teste@email.com', 'Testando', 'Rua dos Testes', '30', '12345-000', '2000', 'ELIGIBLE', '2021-03-10T08:47:08.644');

INSERT INTO tb_cards(id, card_number, holder, card_limit, created_at, status, proposal_id)
VALUES (1, '5209-1622-1164-3586', 'Testador', 1000, '2021-03-10T08:47:08.644', 'ACTIVE', 1);

INSERT INTO tb_cards(id, card_number, holder, card_limit, created_at, status, proposal_id)
VALUES (2, '5209-1622-1164-6666', 'Email', 1000, '2021-03-10T08:47:08.644', 'ACTIVE', 2);

INSERT INTO tb_cards(id, card_number, holder, card_limit, created_at, status, proposal_id)
VALUES (3, '5209-1622-1164-9999', 'Testando', 1000, '2021-03-10T08:47:08.644', 'ACTIVE', 3);

INSERT INTO tb_cardsblock(id, card_id, user_ip, user_agent, created_at)
VALUES (1, 1, '0.0.0.0.0', 'User-Agent', '2021-03-10T08:47:08.644');

UPDATE tb_cards SET status = 'BLOCKED' WHERE id = 1;

INSERT INTO tb_wallets(id,association_id, email, wallet_service, card_id)
VALUES (1, 'd52tes68-3t32-4df7-a1w7-49a53l0l52et','email@email.com', 'PAYPAL', 2);
