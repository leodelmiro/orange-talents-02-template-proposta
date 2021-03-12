INSERT INTO tb_proposals(id, document, email, name, address, number, cep, salary, status, created_at)
VALUES (1, '45523947032', 'teste@teste.com', 'Testador', 'Rua dos Testes', '30', '12345-000', '2000', 1, '2021-03-10T08:47:08.644');

INSERT INTO tb_proposals(id, document, email, name, address, number, cep, salary, status, created_at)
VALUES (2, '71354141016', 'email@email.com', 'Email', 'Rua dos Testes', '30', '12345-000', '2000', 1, '2021-03-10T08:47:08.644');

INSERT INTO tb_cards(id, card_number, holder, card_limit, created_at, proposal_id)
VALUES (1, '5209-1622-1164-3586', 'Testador', 1000, '2021-03-10T08:47:08.644', 1);

INSERT INTO tb_cards(id, card_number, holder, card_limit, created_at, proposal_id)
VALUES (2, '5209-1622-1164-6666', 'Email', 1000, '2021-03-10T08:47:08.644', 2);

INSERT INTO tb_cardsblock(id, card_id, user_ip, user_agent, created_at)
VALUES (1, 1, '0.0.0.0.0', 'User-Agent', '2021-03-10T08:47:08.644');